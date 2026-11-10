package com.checkout.frames.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class SecureWindowRegistryTest {

    private class FakeTarget(
        var secure: Boolean,
        override val key: Any = Any(),
    ) : SecureTarget {
        var setCalls = 0
        override fun isSecure(): Boolean = secure
        override fun setSecure(secure: Boolean) {
            setCalls++
            this.secure = secure
        }
    }

    @Test
    fun `when window is not secure then acquire sets and release clears the flag`() {
        val target = FakeTarget(secure = false)

        SecureWindowRegistry.acquire(target)
        assertTrue(target.secure)

        SecureWindowRegistry.release(target)
        assertFalse(target.secure)
    }

    @Test
    fun `when window is already secure then flag is left untouched`() {
        val target = FakeTarget(secure = true)

        SecureWindowRegistry.acquire(target)
        SecureWindowRegistry.release(target)

        assertTrue(target.secure)
        assertEquals(0, target.setCalls)
    }

    @Test
    fun `when two components share a window then flag is cleared only after the last release`() {
        val key = Any()
        val first = FakeTarget(secure = false, key = key)
        val second = FakeTarget(secure = false, key = key)

        SecureWindowRegistry.acquire(first)
        SecureWindowRegistry.acquire(second)
        SecureWindowRegistry.release(first)
        assertTrue(first.secure)

        SecureWindowRegistry.release(second)
        assertFalse(second.secure)
        assertEquals(1, first.setCalls)
        assertEquals(1, second.setCalls)
    }

    @Test
    fun `when release is called without acquire then nothing happens`() {
        val target = FakeTarget(secure = false)

        SecureWindowRegistry.release(target)

        assertEquals(0, target.setCalls)
    }
}
