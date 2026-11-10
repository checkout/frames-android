package com.checkout.frames.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class SecureWindowRegistryTest {

    /** Window state shared by every target on the same window. */
    private class FakeWindow(var flagSet: Boolean) {
        var setCalls = 0
        var frameCallback: (() -> Unit)? = null
    }

    private class FakeTarget(private val window: FakeWindow) : SecureTarget {
        override val key: Any get() = window

        override fun isSecure(): Boolean = window.flagSet

        override fun setSecure(secure: Boolean) {
            window.setCalls++
            window.flagSet = secure
        }

        override fun watch(onFrame: () -> Unit): () -> Unit {
            window.frameCallback = onFrame
            return { window.frameCallback = null }
        }
    }

    @Test
    fun `when window is not secure then acquire sets and release clears the flag`() {
        val window = FakeWindow(flagSet = false)
        val target = FakeTarget(window)

        SecureWindowRegistry.acquire(target)
        assertTrue(window.flagSet)

        SecureWindowRegistry.release(target)
        assertFalse(window.flagSet)
    }

    @Test
    fun `when window is already secure then flag is left untouched`() {
        val window = FakeWindow(flagSet = true)
        val target = FakeTarget(window)

        SecureWindowRegistry.acquire(target)
        SecureWindowRegistry.release(target)

        assertTrue(window.flagSet)
        assertEquals(0, window.setCalls)
    }

    @Test
    fun `when two components share a window then flag is cleared only after the last release`() {
        val window = FakeWindow(flagSet = false)
        val first = FakeTarget(window)
        val second = FakeTarget(window)

        SecureWindowRegistry.acquire(first)
        SecureWindowRegistry.acquire(second)
        SecureWindowRegistry.release(first)
        assertTrue(window.flagSet)

        SecureWindowRegistry.release(second)
        assertFalse(window.flagSet)
        assertEquals(2, window.setCalls)
    }

    @Test
    fun `when release is called without acquire then nothing happens`() {
        val window = FakeWindow(flagSet = false)

        SecureWindowRegistry.release(FakeTarget(window))

        assertEquals(0, window.setCalls)
    }

    @Test
    fun `when flag is cleared by someone else while held then it is set again on the next frame`() {
        val window = FakeWindow(flagSet = false)
        val target = FakeTarget(window)
        SecureWindowRegistry.acquire(target)
        val onFrame = requireNotNull(window.frameCallback)

        window.flagSet = false
        onFrame()
        assertTrue(window.flagSet)

        SecureWindowRegistry.release(target)
    }

    @Test
    fun `when flag is still set then the frame check does nothing`() {
        val window = FakeWindow(flagSet = false)
        val target = FakeTarget(window)
        SecureWindowRegistry.acquire(target)
        val callsAfterAcquire = window.setCalls

        requireNotNull(window.frameCallback)()

        assertEquals(callsAfterAcquire, window.setCalls)
        SecureWindowRegistry.release(target)
    }

    @Test
    fun `when last component is released then frame checks stop`() {
        val window = FakeWindow(flagSet = false)
        val target = FakeTarget(window)
        SecureWindowRegistry.acquire(target)

        SecureWindowRegistry.release(target)

        assertNull(window.frameCallback)
    }
}
