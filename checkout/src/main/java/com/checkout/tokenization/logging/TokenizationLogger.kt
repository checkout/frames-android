package com.checkout.tokenization.logging

import com.checkout.network.response.ErrorResponse
import com.checkout.tokenization.response.TokenDetailsResponse

/**
 * Used to log events related to tokenization.
 */
internal interface TokenizationLogger {

    /**
     * Logs error event while generating token request.
     */
    fun logErrorOnTokenRequestedEvent(tokenType: String, publicKey: String, error: Throwable? = null)

    /**
     * Logs event for token request.
     */
    fun logTokenRequestEvent(tokenType: String, publicKey: String)

    /**
     * Logs event for token response.
     */
    fun logTokenResponseEvent(
        tokenType: String,
        publicKey: String,
        tokenDetails: TokenDetailsResponse? = null,
        code: Int? = null,
        errorResponse: ErrorResponse? = null,
    )

    /**
     * Logs event for reset correlationId.
     */
    fun resetSession()
}
