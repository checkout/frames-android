package com.checkout.tokenization.model

public data class TokenDetails(
    val type: String,

    val token: String,

    val expiresOn: String,

    val expiryMonth: Int,

    val expiryYear: Int,

    val scheme: String?,

    val schemeLocal: String?,

    val last4: String,

    val bin: String,

    val cardType: String?,

    val cardCategory: String?,

    val issuer: String?,

    val issuerCountry: String?,

    val productId: String? = null,

    val productType: String? = null,

    val billingAddress: Address? = null,

    val phone: Phone? = null,

    val tokenFormat: String? = null,

    val name: String?,
)
