package com.checkout.android_sdk.threeds

import android.view.View
import androidx.test.core.app.ApplicationProvider
import com.checkout.android_sdk.FramesLogger
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class ThreedsWebViewTest {

    private lateinit var mockFramesLogger: FramesLogger

    private lateinit var threedsWebView: ThreedsWebView

    @Before
    internal fun setUp() {
        mockFramesLogger = Mockito.mock(FramesLogger::class.java)
    }

    @Test
    fun `test initialization of ThreedsWebView with url to load`() {
        val expectedUrlToLoad = "https://www.example.com"

        threedsWebView = ThreedsWebView(context = ApplicationProvider.getApplicationContext(),
            urlToLoad = expectedUrlToLoad)

        assertEquals(expectedUrlToLoad, threedsWebView.urlToLoad)
    }

    @Test
    fun `test view visibility of threedsWebviewPresented`() {
        val expectedViewVisibility = View.VISIBLE

        threedsWebView = ThreedsWebView(context = ApplicationProvider.getApplicationContext(),
            urlToLoad = "https://www.example.com")

        assertEquals(expectedViewVisibility, threedsWebView.visibility)

    }

}