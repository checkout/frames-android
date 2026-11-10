package com.checkout.frames.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.MainThread
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalView
import java.util.WeakHashMap

/**
 * Sets [WindowManager.LayoutParams.FLAG_SECURE] on the window that hosts the calling composable for as long as it
 * stays in the composition. This keeps card data out of the Recents snapshot, screenshots and screen capture.
 *
 * The window is resolved from the view's root, so dialogs and bottom sheets that host the SDK UI get the flag on
 * their own window rather than on the Activity window behind them.
 */
@Composable
internal fun SecureWindowEffect() {
    val view = LocalView.current
    DisposableEffect(view) {
        val target = view.findSecureTarget()
        target?.let { SecureWindowRegistry.acquire(it) }
        onDispose { target?.let { SecureWindowRegistry.release(it) } }
    }
}

private fun View.findSecureTarget(): SecureTarget? {
    val activityWindow = context.findActivity()?.window
    val root = rootView
    if (activityWindow != null && activityWindow.peekDecorView() === root) return WindowSecureTarget(activityWindow)

    val rootParams = root.layoutParams as? WindowManager.LayoutParams
    if (rootParams != null && root.isAttachedToWindow) return RootViewSecureTarget(root, rootParams)

    return activityWindow?.let { WindowSecureTarget(it) }
}

private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

internal interface SecureTarget {
    /** Identifies the underlying window. Targets for the same window must return the same key. */
    val key: Any
    fun isSecure(): Boolean
    fun setSecure(secure: Boolean)
}

private class WindowSecureTarget(private val window: Window) : SecureTarget {
    override val key: Any get() = window

    override fun isSecure(): Boolean = (window.attributes.flags and WindowManager.LayoutParams.FLAG_SECURE) != 0

    override fun setSecure(secure: Boolean) {
        if (secure) {
            window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }
}

/**
 * A window the SDK can't reach through an Activity, such as a View-based dialog or bottom sheet. The root view's
 * [WindowManager.LayoutParams] are that window's attributes, so updating them updates the window.
 */
private class RootViewSecureTarget(
    private val root: View,
    private val params: WindowManager.LayoutParams,
) : SecureTarget {
    override val key: Any get() = root

    override fun isSecure(): Boolean = (params.flags and WindowManager.LayoutParams.FLAG_SECURE) != 0

    override fun setSecure(secure: Boolean) {
        params.flags = if (secure) {
            params.flags or WindowManager.LayoutParams.FLAG_SECURE
        } else {
            params.flags and WindowManager.LayoutParams.FLAG_SECURE.inv()
        }
        if (root.isAttachedToWindow) {
            val windowManager = root.context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
            windowManager?.updateViewLayout(root, params)
        }
    }
}

/**
 * Reference-counts secure requests per window, so several SDK components can share a window, and clears the flag
 * only if the SDK set it.
 */
@MainThread
internal object SecureWindowRegistry {

    private class Entry(var count: Int, val setBySdk: Boolean)

    private val entries = WeakHashMap<Any, Entry>()

    fun acquire(target: SecureTarget) {
        val entry = entries[target.key]
        if (entry != null) {
            entry.count++
            return
        }
        val alreadySecure = target.isSecure()
        if (!alreadySecure) target.setSecure(true)
        entries[target.key] = Entry(count = 1, setBySdk = !alreadySecure)
    }

    fun release(target: SecureTarget) {
        val entry = entries[target.key] ?: return
        entry.count--
        if (entry.count > 0) return
        entries.remove(target.key)
        if (entry.setBySdk) target.setSecure(false)
    }
}
