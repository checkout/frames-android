package com.checkout.network.utils

internal object OkHttpConstants {
    const val CALL_TIMEOUT_MS = 10000L
    const val CONNECTION_TIMEOUT_MS = 10000L
    const val READ_TIMEOUT_MS = 30000L

    const val HEADER_AUTHORIZATION = "Authorization"
    const val HEADER_USER_AGENT_NAME = "User-Agent"
    const val HEADER_USER_AGENT_VALUE = "checkout-android-sdk"
}
