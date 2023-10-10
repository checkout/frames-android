package com.checkout.tokenization.error

import com.checkout.base.error.CheckoutError

internal class TokenizationError(
    errorCode: String,
    message: String? = null,
    cause: Throwable? = null,
) : CheckoutError(
    errorCode,
    message,
    cause,
) {
    companion object {
        const val INVALID_TOKEN_REQUEST = "TokenizationError.Server:3000"
        const val INVALID_KEY = "TokenizationError.Server:3002"
        const val TOKENIZATION_API_RESPONSE_PROCESSING_ERROR = "TokenizationError.Server:3003"
        const val TOKENIZATION_API_MALFORMED_JSON = "TokenizationError.Server:3004"
        const val GOOGLE_PAY_REQUEST_PARSING_ERROR = "TokenizationError.Server:3005"
    }
}
