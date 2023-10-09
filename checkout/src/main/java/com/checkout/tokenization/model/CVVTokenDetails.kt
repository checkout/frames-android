package com.checkout.tokenization.model

/**
 * A representation of a [CVVTokenDetails] contains tokenization response
 */
public data class CVVTokenDetails(
    /**
     * The type of tokenization
     */
    val type: String,

    /**
     * The reference token
     */
    val token: String,

    /**
     * The date/time the token will expire
     */
    val expiresOn: String,
)
