package com.checkout.threedsecure.webview

import android.os.Build
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.checkout.threedsecure.logging.ThreeDSLogger
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class ThreeDSWebViewClientTest {

    @RelaxedMockK
    lateinit var logger: ThreeDSLogger

    private lateinit var client: WebViewClient

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        client = ThreeDSWebViewClient({}, {}, logger)
    }

    @Test
    fun `when page started for the first time then log presented event`() {
        // When
        client.onPageStarted(mockk(), "", null)
        client.onPageStarted(mockk(), "", null)
        client.onPageStarted(mockk(), "", null)

        // Then
        verify(exactly = 1) { logger.logPresentedEvent() }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @Test
    fun `when page committed visible for the first time then log loaded event`() {
        // When
        client.onPageCommitVisible(mockk(), "")
        client.onPageCommitVisible(mockk(), "")
        client.onPageCommitVisible(mockk(), "")

        // Then
        verify(exactly = 1) { logger.logLoadedEvent(true) }
    }
}
