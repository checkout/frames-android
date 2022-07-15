package com.checkout.tokenization.model

/**
 * Used for Creating a card token request
 *
 * @param card the representation of [Card]
 * @param onSuccess invokes when token api returns success response holding the [TokenDetails]
 * @param onFailure invokes when token api returns failure response holding the errorMessage
 */
public data class CardTokenRequest(
    val card: Card,
    val onSuccess: (tokenDetails: TokenDetails) -> Unit,
    val onFailure: (errorMessage: String) -> Unit
)
