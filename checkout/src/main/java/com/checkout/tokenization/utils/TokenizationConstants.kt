package com.checkout.tokenization.utils

import okhttp3.MediaType.Companion.toMediaType

internal object TokenizationConstants {
    val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    // tokenization types
    const val CARD = "card"
    const val GOOGLE_PAY = "googlepay"
}
