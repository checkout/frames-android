package com.checkout.validation.model

/**
 * Object representing a card number validation request, contains [cardNumber] and [isEagerValidation].
 */
internal data class CardNumberValidationRequest(
    /**
     * The card number representing as an [String]
     */
    val cardNumber: String,
    /**
     * The eager validation flag representing as an [Boolean], false by default
     */
    val isEagerValidation: Boolean = false,
)
