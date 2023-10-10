package com.checkout.tokenization.model

/**
 * A representation of a [Card API object]
 */
public data class Card @JvmOverloads constructor(
    /**
     * ExpiryDate of the Card. See [ExpiryDate]
     */
    val expiryDate: ExpiryDate,

    /**
     * Cardholder name.
     */
    val name: String? = null,

    /**
     * Card number.
     */
    val number: String,

    /**
     * Card CVV.
     */
    val cvv: String? = null,

    /**
     * Billing Address [Address]
     */
    val billingAddress: Address? = null,

    /**
     * Cardholder [Phone].
     */
    val phone: Phone? = null,

)
