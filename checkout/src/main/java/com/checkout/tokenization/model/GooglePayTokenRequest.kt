package com.checkout.tokenization.model

/**
 * Used for Creating a Google Pay token request
 *
 * @param tokenJsonPayload the representation of [String]
 * @param onSuccess invokes when token api returns success response holding the [TokenDetails]
 * @param onFailure invokes when token api returns failure response holding the errorMessage
 */
public data class GooglePayTokenRequest(
    val tokenJsonPayload: String,
    val onSuccess: (tokenDetails: TokenDetails) -> Unit,
    val onFailure: (errorMessage: String) -> Unit
)
