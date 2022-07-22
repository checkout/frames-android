package com.checkout.validation.model

import com.checkout.base.model.CardScheme

/**
 * Object representing an cvv validation request, contains [cvv] and [cardScheme].
 */
internal data class CvvValidationRequest(
    /**
     * The cvv representing as an [String]
     */
    val cvv: String,
    /**
     * The card scheme representing as an [CardScheme]
     */
    val cardScheme: CardScheme
)
