package com.checkout.threedsecure.webview

import android.annotation.SuppressLint
import android.content.Context
import android.webkit.WebView
import android.widget.FrameLayout

@SuppressLint("ViewConstructor")
internal class ThreeDSWebView constructor(context: Context) : WebView(context) {

    init {
        setup()
    }

    override fun destroy() {
        cleanup()
        super.destroy()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setup() {
        settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
        }
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
    }

    private fun cleanup() {
        clearHistory()
        onPause()
        removeAllViews()
    }
}
