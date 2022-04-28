package com.checkout.android_sdk.threeds

import android.net.Uri
import android.webkit.*
import com.checkout.android_sdk.FramesLogger
import com.checkout.android_sdk.FramesLogger.Companion.log
import com.checkout.android_sdk.PaymentForm
import com.checkout.android_sdk.View.data.LoggingState

internal class ThreedsWebViewClient(
    val successUrl: String,
    val failUrl: String,
    val loggingState: LoggingState,
    val framesLogger: FramesLogger,
    val m3DSecureListener: PaymentForm.On3DSFinished,
) : WebViewClient() {

    private var webViewLoadedFirstTime = true

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)

        // Listen for when the URL changes and match it with either the success of fail url
        if (url == null) {
            m3DSecureListener.onError("Url is null")
            return
        }
        when {
            url.contains(successUrl) -> {
                sendThreedsWebViewCompleteEvent(getToken(url), true)
                m3DSecureListener.onSuccess(getToken(url))
            }
            url.contains(failUrl) -> {
                sendThreedsWebViewCompleteEvent("", false)
                m3DSecureListener.onError(getToken(url))
            }
        }
    }

    override fun onPageCommitVisible(view: WebView?, url: String?) {
        super.onPageCommitVisible(view, url)
        /* To log the Webview loaded event only once, webViewLoadedFirstTime variable managed the logic to trigger it.*/
        if (webViewLoadedFirstTime) {
            sendThreedsWebViewLoadedEvent(true)
            webViewLoadedFirstTime = false
        }
    }

    // Logging THREEDS_WEBVIEW_LOADED event on failed to load webview event due to network errors
    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?,
    ) {
        sendThreedsWebViewLoadedEvent(false)
        super.onReceivedError(view, request, error)
    }

    private fun sendThreedsWebViewLoadedEvent(isSuccessFullWebviewLoadingEvent: Boolean) {
        if (loggingState.threedsWebviewLoaded) return
        log {
            framesLogger.sendThreedsWebviewLoadedEvent(isSuccessFullWebviewLoadingEvent)
            loggingState.threedsWebviewLoaded = true
        }
    }

    private fun sendThreedsWebViewCompleteEvent(
        tokenID: String?,
        isSuccessFullWebviewCompleteEvent: Boolean,
    ) {
        if (loggingState.threedsWebviewComplete) return
        log {
            framesLogger.sendThreedsWebviewCompleteEvent(tokenID, isSuccessFullWebviewCompleteEvent)
            loggingState.threedsWebviewComplete = true
        }
    }

    // Logging THREEDS_WEBVIEW_LOADED event on failed to load webview event due to http errors
    override fun onReceivedHttpError(
        view: WebView?,
        request: WebResourceRequest?,
        errorResponse: WebResourceResponse?,
    ) {
        sendThreedsWebViewLoadedEvent(false)
        super.onReceivedHttpError(view, request, errorResponse)
    }

    internal fun getToken(redirectUrl: String): String? {
        val uri: Uri = Uri.parse(redirectUrl)
        var token: String? = uri.getQueryParameter("cko-payment-token")
        if (token == null) {
            token = uri.getQueryParameter("cko-session-id")
        }
        return token
    }

}