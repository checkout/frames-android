package com.checkout.network.error

import com.checkout.base.error.CheckoutError

internal class NetworkError(
    errorCode: String,
    message: String? = null,
    cause: Throwable? = null,
) : CheckoutError(
    errorCode,
    message,
    cause,
) {
    companion object {
        const val CONNECTION_FAILED_ERROR = "NetworkError:2001"
        const val RESPONSE_PARSING_ERROR = "NetworkError:2007"
    }
}
