package com.checkout.tokenization.model

/**
 * Used for Creating a CVV token request
 *
 * @param onSuccess invokes when token api returns success response holding the [CVVTokenDetails]
 * @param onFailure invokes when token api returns failure response holding the errorMessage
 */
public data class CVVTokenRequest(
    val onSuccess: (tokenDetails: CVVTokenDetails) -> Unit,
    val onFailure: (errorMessage: String) -> Unit
)
