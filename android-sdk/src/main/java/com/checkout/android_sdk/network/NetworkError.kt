package com.checkout.android_sdk.network

import com.checkout.android_sdk.network.utils.NetworkResponse

class NetworkError(
    val networkResponse: NetworkResponse? = null,
    message: String? = null,
    cause: Throwable? = null
): Exception(message, cause) {

    var networkTimeMs: Long = 0
        internal set
}