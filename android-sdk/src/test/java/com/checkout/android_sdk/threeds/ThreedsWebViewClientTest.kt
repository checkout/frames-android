package com.checkout.android_sdk.threeds

import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import androidx.test.core.app.ApplicationProvider
import com.checkout.android_sdk.FramesLogger
import com.checkout.android_sdk.PaymentForm
import com.checkout.android_sdk.View.data.LoggingState
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class ThreedsWebViewClientTest {

    private lateinit var mockFramesLogger: FramesLogger

    private lateinit var mockLoggingState: LoggingState

    private lateinit var mock3DSecureListener: PaymentForm.On3DSFinished

    private val webView = WebView(ApplicationProvider.getApplicationContext())

    private lateinit var mockWebResourceRequest: WebResourceRequest

    private lateinit var mockWebResourceError: WebResourceError

    private lateinit var mockWebResourceResponse: WebResourceResponse

    @Before
    internal fun setUp() {
        mockFramesLogger = Mockito.mock(FramesLogger::class.java)
        mockLoggingState = Mockito.mock(LoggingState::class.java)
        mock3DSecureListener = Mockito.mock(PaymentForm.On3DSFinished::class.java)
        mockWebResourceRequest = Mockito.mock(WebResourceRequest::class.java)
        mockWebResourceError = Mockito.mock(WebResourceError::class.java)
        mockWebResourceResponse = Mockito.mock(WebResourceResponse::class.java)
    }

    @Test
    fun `test initialization of threedsWebViewClient with urls`() {
        val expectedSuccessUrl = "https://www.successurl.com/"
        val expectedFailUrl = "https://www.failurl.com/"
        val threedsWebViewClient =
            getThreedsWebViewClient(expectedSuccessUrl, expectedFailUrl)

        assertEquals(expectedSuccessUrl, threedsWebViewClient.successUrl)
        assertEquals(expectedFailUrl, threedsWebViewClient.failUrl)
    }

    @Test
    fun `test logging of threedsWebviewCompleteEvent for success url`() {
        val threedsWebViewClient =
            getThreedsWebViewClient("https://www.successurl.com/", "https://www.failurl.com/")

        threedsWebViewClient.onPageFinished(webView, threedsWebViewClient.successUrl)

        Mockito.verify(mockFramesLogger, Mockito.times(1)).sendThreedsWebviewCompleteEvent(
            threedsWebViewClient.getToken(threedsWebViewClient.successUrl),
            true)
    }

    @Test
    fun `test logging of threedsWebviewCompleteEvent for failed url`() {
        val threedsWebViewClient =
            getThreedsWebViewClient("https://www.successurl.com/", "https://www.failurl.com/")

        threedsWebViewClient.onPageFinished(webView, threedsWebViewClient.failUrl)

        Mockito.verify(mockFramesLogger, Mockito.times(1)).sendThreedsWebviewCompleteEvent(
            "",
            false)
    }

    @Test
    fun `test logging of threedsWebviewLoaded for successful loading of page for only once at session`() {
        val initialUrl = "https://www.example.com"
        val threedsWebViewClient =
            getThreedsWebViewClient("https://www.successurl.com/", "https://www.failurl.com/")

        // call onPageCommitVisible for initial url to load (this callback calling everytime when any url is loaded)
        threedsWebViewClient.onPageCommitVisible(webView, initialUrl)
        // call onPageCommitVisible for final success url to load
        threedsWebViewClient.onPageCommitVisible(webView, threedsWebViewClient.successUrl)

        Mockito.verify(mockFramesLogger, Mockito.times(1)).sendThreedsWebviewLoadedEvent(true)
    }

    @Test
    fun `test logging of threedsWebviewLoaded for failed loading of page due to network errors`() {
        val threedsWebViewClient =
            getThreedsWebViewClient("https://www.successurl.com/", "https://www.failurl.com/")

        threedsWebViewClient.onReceivedError(webView, mockWebResourceRequest, mockWebResourceError)

        Mockito.verify(mockFramesLogger, Mockito.times(1)).sendThreedsWebviewLoadedEvent(false)
    }

    @Test
    fun `test logging of threedsWebviewLoaded for failed loading of page due to http errors`() {
        val threedsWebViewClient =
            getThreedsWebViewClient("https://www.successurl.com/", "https://www.failurl.com/")

        threedsWebViewClient.onReceivedHttpError(webView,
            mockWebResourceRequest,
            mockWebResourceResponse)

        Mockito.verify(mockFramesLogger, Mockito.times(1)).sendThreedsWebviewLoadedEvent(false)
    }

    @Test
    fun `test success logging of threedsWebviewPresented`() {
        val firstUrlToLoad = "https://www.example.com"
        val secondUrlToLoad = "https://www.w3.org/Provider/Style/dummy.html"
        val threedsWebViewClient =
            getThreedsWebViewClient("https://www.successurl.com/", "https://www.failurl.com/")

        threedsWebViewClient.onPageStarted(webView, firstUrlToLoad, null)

        threedsWebViewClient.onPageStarted(webView, secondUrlToLoad, null)

        Mockito.verify(mockFramesLogger, Mockito.times(1)).sendThreedsWebviewPresentedEvent()
    }

    private fun getThreedsWebViewClient(
        expectedSuccessUrl: String,
        expectedFailedUrl: String,
    ): ThreedsWebViewClient {
        return ThreedsWebViewClient(successUrl = expectedSuccessUrl,
            failUrl = expectedFailedUrl,
            loggingState = mockLoggingState,
            framesLogger = mockFramesLogger,
            m3DSecureListener = mock3DSecureListener)
    }
}