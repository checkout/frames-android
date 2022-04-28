package com.checkout.android_sdk.threeds

import android.annotation.SuppressLint
import android.content.Context
import android.webkit.WebView
import com.checkout.android_sdk.FramesLogger
import com.checkout.android_sdk.FramesLogger.Companion.log
import com.checkout.android_sdk.View.data.LoggingState

@SuppressLint("ViewConstructor")
internal class ThreedsWebView constructor(
    context: Context,
    val urlToLoad: String,
    private val loggingState: LoggingState,
    private val framesLogger: FramesLogger,
) : WebView(context) {

    init {
        loadUrl(urlToLoad)
        configureSettings()
        if (visibility == VISIBLE) {
            sendThreedsWebViewPresentedEvent()
        }
    }

    private fun sendThreedsWebViewPresentedEvent() {
        if (loggingState.threedsWebviewPresented) return
        log {
            framesLogger.sendThreedsWebviewPresentedEvent()
            loggingState.threedsWebviewPresented = true
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configureSettings() {
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
    }

    override fun destroy() {
        cleanup()
        super.destroy()
    }

    private fun cleanup() {
        clearHistory()
        onPause()
        removeAllViews()
    }
}