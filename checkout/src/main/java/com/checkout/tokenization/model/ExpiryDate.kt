package com.checkout.tokenization.model

/**
 * Object representing an expiry date containing [expiryMonth] and [expiryYear].
 */
public data class ExpiryDate(
    /**
     * The expiry month representing as an [Int] value (E.g 1 = January and 12 = December)
     */
    val expiryMonth: Int,
    /**
     * The expiry year representing as an [Int] value YYYY format. (E.g. 2021)
     */
    val expiryYear: Int,
)
