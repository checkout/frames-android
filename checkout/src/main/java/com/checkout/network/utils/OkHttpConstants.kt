package com.checkout.network.utils

import com.checkout.BuildConfig

internal object OkHttpConstants {
    const val CALL_TIMEOUT_MS = 10000L
    const val CONNECTION_TIMEOUT_MS = 10000L
    const val READ_TIMEOUT_MS = 30000L

    const val HEADER_AUTHORIZATION = "Authorization"
    const val HEADER_USER_AGENT_NAME = "User-Agent"
    const val HEADER_USER_AGENT_VALUE = "checkout-sdk-android/${BuildConfig.PRODUCT_VERSION}"
}
