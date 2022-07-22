package com.checkout.validation.model

/**
 * Object representing an expiry date validation request, contains [expiryMonth] and [expiryYear].
 */
internal data class ExpiryDateValidationRequest(
    /**
     * The expiry month representing as an [String] value (E.g 1 = January and 12 = December)
     */
    val expiryMonth: String,
    /**
     * The expiry year representing as an [String] value YYYY format. (E.g. 2021)
     */
    val expiryYear: String
)
