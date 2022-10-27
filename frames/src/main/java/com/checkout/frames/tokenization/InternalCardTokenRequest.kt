package com.checkout.frames.tokenization

import com.checkout.tokenization.model.Card

/**
 * Used for creating of [CardTokenRequest].
 * Should be used to provide internal UI related in-SDK callbacks.
 *
 * @param card the representation of [Card]
 * @param onSuccess invokes when token api returns success response
 * @param onFailure invokes when token api returns failure response
 */
internal data class InternalCardTokenRequest(
    val card: Card,
    val onSuccess: () -> Unit,
    val onFailure: () -> Unit
)
