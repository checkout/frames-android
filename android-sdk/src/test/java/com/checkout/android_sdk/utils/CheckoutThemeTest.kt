package com.checkout.android_sdk.utils

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.checkout.android_sdk.R
import com.checkout.android_sdk.Utils.CheckoutTheme
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CheckoutThemeTest {

    private lateinit var mockContext: Context

    private lateinit var checkoutTheme: CheckoutTheme

    @Before
    internal fun setUp() {
        mockContext = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `test colorPrimaryProperty map to correct values`() {
        val expectedColorPrimaryProperty = mutableMapOf<String, Any?>()
        expectedColorPrimaryProperty["alpha"] = 255
        expectedColorPrimaryProperty["red"] = 63
        expectedColorPrimaryProperty["green"] = 81
        expectedColorPrimaryProperty["blue"] = 181

        // setting up custom theme to unit test
        mockContext.setTheme(R.style.AppTheme)

        checkoutTheme = CheckoutTheme(mockContext)

        assertEquals(expectedColorPrimaryProperty, checkoutTheme.getColorPrimaryProperty())
    }

    @Test
    fun `test colorAccentProperty map to correct values`() {
        val expectedColorPrimaryProperty = mutableMapOf<String, Any?>()
        expectedColorPrimaryProperty["alpha"] = 255
        expectedColorPrimaryProperty["red"] = 255
        expectedColorPrimaryProperty["green"] = 64
        expectedColorPrimaryProperty["blue"] = 129

        // setting up custom theme to unit test
        mockContext.setTheme(R.style.CustomTheme)

        checkoutTheme = CheckoutTheme(mockContext)

        assertEquals(expectedColorPrimaryProperty, checkoutTheme.getColorAccentProperty())
    }

    @Test
    fun `test colorButtonNormalProperty map to correct values`() {
        val expectedColorPrimaryProperty = mutableMapOf<String, Any?>()
        expectedColorPrimaryProperty["alpha"] = 255
        expectedColorPrimaryProperty["red"] = 9
        expectedColorPrimaryProperty["green"] = 167
        expectedColorPrimaryProperty["blue"] = 113

        // setting up custom theme to unit test
        mockContext.setTheme(R.style.CustomTheme)

        checkoutTheme = CheckoutTheme(mockContext)

        assertEquals(expectedColorPrimaryProperty, checkoutTheme.getColorButtonNormalProperty())
    }

    @Test
    fun `test textColorPrimaryProperty map to correct values`() {
        val expectedColorPrimaryProperty = mutableMapOf<String, Any?>()
        expectedColorPrimaryProperty["alpha"] = 255
        expectedColorPrimaryProperty["red"] = 0
        expectedColorPrimaryProperty["green"] = 0
        expectedColorPrimaryProperty["blue"] = 0

        // setting up custom theme to unit test
        mockContext.setTheme(R.style.CustomTheme)

        checkoutTheme = CheckoutTheme(mockContext)

        assertEquals(expectedColorPrimaryProperty, checkoutTheme.getTextColorPrimaryProperty())
    }

    @Test
    fun `test colorControlActivatedProperty map to correct values`() {
        val expectedColorPrimaryProperty = mutableMapOf<String, Any?>()
        expectedColorPrimaryProperty["alpha"] = 255
        expectedColorPrimaryProperty["red"] = 9
        expectedColorPrimaryProperty["green"] = 167
        expectedColorPrimaryProperty["blue"] = 113

        // setting up custom theme to unit test
        mockContext.setTheme(R.style.CustomTheme)

        checkoutTheme = CheckoutTheme(mockContext)

        assertEquals(expectedColorPrimaryProperty, checkoutTheme.getColorControlActivatedProperty())
    }

}