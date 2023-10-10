package com.checkout.threedsecure.webview

import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.checkout.threedsecure.error.ThreeDSError
import com.checkout.threedsecure.logging.ThreeDSLogger
import com.checkout.threedsecure.utils.toThreeDSError

internal class ThreeDSWebViewClient(
    private val onResult: (url: String?) -> Boolean,
    private val onError: (error: ThreeDSError) -> Unit,
    private val logger: ThreeDSLogger,
) : WebViewClient() {

    private var presentedFirstTime = true
    private var loadedFirstTime = true

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)

        if (presentedFirstTime) {
            logger.logPresentedEvent()
            presentedFirstTime = false
        }
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return request?.url?.let { onResult(it.toString()) } ?: super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageCommitVisible(view: WebView?, url: String?) {
        super.onPageCommitVisible(view, url)

        if (loadedFirstTime) {
            logger.logLoadedEvent(true)
            loadedFirstTime = false
        }
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?,
    ) {
        super.onReceivedError(view, request, error)
        error?.toThreeDSError()?.let(onError)
    }

    override fun onReceivedHttpError(
        view: WebView?,
        request: WebResourceRequest?,
        errorResponse: WebResourceResponse?,
    ) {
        super.onReceivedHttpError(view, request, errorResponse)
        errorResponse?.toThreeDSError()?.let(onError)
    }
}
