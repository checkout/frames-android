package com.checkout.frames.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowManager
import androidx.annotation.MainThread
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.DialogWindowProvider
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Sets [WindowManager.LayoutParams.FLAG_SECURE] on the window that hosts the calling composable while the composable
 * is in the composition and its view is attached to a window. This keeps the payment UI out of the Recents snapshot,
 * screenshots and screen capture.
 */
@Composable
internal fun SecureWindowEffect() {
    val view = LocalView.current
    DisposableEffect(view) {
        val binding = SecureWindowBinding(view)
        binding.start()
        onDispose { binding.stop() }
    }
}

/**
 * Follows the view's attach state, so the flag moves with the view if it is detached or reattached to another
 * window while the composition stays alive.
 */
@MainThread
private class SecureWindowBinding(private val view: View) : View.OnAttachStateChangeListener {

    private var target: SecureTarget? = null

    fun start() {
        view.addOnAttachStateChangeListener(this)
        if (view.isAttachedToWindow) acquire()
    }

    fun stop() {
        view.removeOnAttachStateChangeListener(this)
        release()
    }

    override fun onViewAttachedToWindow(v: View) = acquire()

    override fun onViewDetachedFromWindow(v: View) = release()

    private fun acquire() {
        if (target != null) return
        target = view.findSecureTarget()?.also { SecureWindowRegistry.acquire(it) }
    }

    private fun release() {
        target?.let { SecureWindowRegistry.release(it) }
        target = null
    }
}

private fun View.findSecureTarget(): SecureTarget? {
    val root = rootView
    findOwningWindow(root)?.let { return WindowSecureTarget(it, root) }
    if (root.layoutParams is WindowManager.LayoutParams) return RootViewSecureTarget(root)
    return null
}

/** Finds the [Window] whose decor view is [root]: the Activity, a Compose dialog or a [DialogFragment]. */
private fun View.findOwningWindow(root: View): Window? =
    sequenceOf(
        { context.findActivity()?.window },
        { findComposeDialogWindow() },
        { findDialogFragmentWindow() },
    ).firstNotNullOfOrNull { candidate -> candidate()?.takeIf { it.peekDecorView() === root } }

private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

private fun View.findComposeDialogWindow(): Window? =
    generateSequence(parent) { it.parent }.filterIsInstance<DialogWindowProvider>().firstOrNull()?.window

private fun View.findDialogFragmentWindow(): Window? {
    val fragment = try {
        FragmentManager.findFragment<Fragment>(this)
    } catch (ignored: IllegalStateException) {
        return null
    }
    return generateSequence(fragment) { it.parentFragment }
        .filterIsInstance<DialogFragment>()
        .firstNotNullOfOrNull { it.dialog?.window }
}

internal interface SecureTarget {
    /** Identifies the underlying window. Targets for the same window must return the same key. */
    val key: Any

    fun isSecure(): Boolean

    fun setSecure(secure: Boolean)

    /**
     * Calls [onFrame] before each frame is drawn.
     *
     * @return a function that stops the callbacks.
     */
    fun onEachFrame(onFrame: () -> Unit): () -> Unit
}

private abstract class RootSecureTarget(protected val root: View) : SecureTarget {
    override val key: Any get() = root

    override fun onEachFrame(onFrame: () -> Unit): () -> Unit {
        val listener = ViewTreeObserver.OnPreDrawListener {
            onFrame()
            // Never skip a frame: the host may clear the flag on every frame, and skipping would stall drawing.
            true
        }
        root.viewTreeObserver.addOnPreDrawListener(listener)
        return {
            val observer = root.viewTreeObserver
            if (observer.isAlive) observer.removeOnPreDrawListener(listener)
        }
    }
}

/** A window the SDK can reach directly. Updating the [Window] keeps its own copy of the attributes in step. */
private class WindowSecureTarget(private val window: Window, root: View) : RootSecureTarget(root) {

    override fun isSecure(): Boolean = window.attributes.flags.hasSecureFlag()

    override fun setSecure(secure: Boolean) {
        if (secure) {
            window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }
}

/**
 * A window the SDK can't reach through an Activity, a Compose dialog or a [DialogFragment], such as a View-based
 * dialog shown by the host app. The root view's current [WindowManager.LayoutParams] are read on every call, so
 * newer parameters set by the host are never replaced with an old copy.
 */
private class RootViewSecureTarget(root: View) : RootSecureTarget(root) {

    private val params: WindowManager.LayoutParams?
        get() = root.layoutParams as? WindowManager.LayoutParams

    override fun isSecure(): Boolean = params?.flags?.hasSecureFlag() ?: false

    override fun setSecure(secure: Boolean) {
        val current = params ?: return
        val previousFlags = current.flags
        current.flags = if (secure) {
            previousFlags or WindowManager.LayoutParams.FLAG_SECURE
        } else {
            previousFlags and WindowManager.LayoutParams.FLAG_SECURE.inv()
        }
        if (!root.isAttachedToWindow) return
        val windowManager = root.context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        val applied = windowManager != null && try {
            windowManager.updateViewLayout(root, current)
            true
        } catch (ignored: IllegalArgumentException) {
            // The window is being removed.
            false
        }
        // Keep the params in step with the window, so a failed update is retried on the next frame.
        if (!applied) current.flags = previousFlags
    }
}

private fun Int.hasSecureFlag(): Boolean = (this and WindowManager.LayoutParams.FLAG_SECURE) != 0

/**
 * Reference-counts secure requests per window, so several SDK components can share a window, and clears the flag
 * only if the SDK set it.
 *
 * While a window is held, the flag is checked before each frame and set again if something else cleared it, for
 * example a dialog republishing its attributes or a Compose dialog reapplying its default secure policy.
 *
 * Whether the SDK set the flag is decided when the window is first acquired. If the host app sets the flag itself
 * while the SDK holds the window, the SDK still clears it on the last release.
 */
@MainThread
internal object SecureWindowRegistry {

    private class Entry(var count: Int, val setBySdk: Boolean, val stopWatching: () -> Unit)

    private val entries = HashMap<Any, Entry>()

    fun acquire(target: SecureTarget) {
        val entry = entries[target.key]
        if (entry != null) {
            entry.count++
            return
        }
        val alreadySecure = target.isSecure()
        if (!alreadySecure) target.setSecure(true)
        val stopWatching = target.onEachFrame {
            if (!target.isSecure()) target.setSecure(true)
        }
        entries[target.key] = Entry(count = 1, setBySdk = !alreadySecure, stopWatching = stopWatching)
    }

    fun release(target: SecureTarget) {
        val entry = entries[target.key] ?: return
        entry.count--
        if (entry.count > 0) return
        entries.remove(target.key)
        entry.stopWatching()
        if (entry.setBySdk) target.setSecure(false)
    }
}
