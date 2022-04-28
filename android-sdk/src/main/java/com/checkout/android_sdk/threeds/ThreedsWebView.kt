package com.checkout.android_sdk.threeds

import android.annotation.SuppressLint
import android.content.Context
import android.webkit.WebView

@SuppressLint("ViewConstructor")
internal class ThreedsWebView constructor(
    context: Context,
    val urlToLoad: String,
) : WebView(context) {

    init {
        loadUrl(urlToLoad)
        configureSettings()
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