package com.checkout.tokenization.response

import com.checkout.tokenization.entity.AddressEntity
import com.checkout.tokenization.entity.PhoneEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Token details returns as the response to cardTokenization call.
 */
internal typealias GetTokenDetailsResponse = TokenDetailsResponse

@JsonClass(generateAdapter = true)
internal data class TokenDetailsResponse(
    @Json(name = "type")
    val type: String,

    @Json(name = "token")
    val token: String,

    @Json(name = "expires_on")
    val expiresOn: String,

    @Json(name = "expiry_month")
    val expiryMonth: Int,

    @Json(name = "expiry_year")
    val expiryYear: Int,

    @Json(name = "scheme")
    val scheme: String?,

    @Json(name = "last4")
    val last4: String,

    @Json(name = "bin")
    val bin: String,

    @Json(name = "card_type")
    val cardType: String?,

    @Json(name = "card_category")
    val cardCategory: String?,

    @Json(name = "issuer")
    val issuer: String?,

    @Json(name = "issuer_country")
    val issuerCountry: String?,

    @Json(name = "product_id")
    val productId: String? = null,

    @Json(name = "product_type")
    val productType: String? = null,

    @Json(name = "billing_address")
    val billingAddress: AddressEntity? = null,

    @Json(name = "phone")
    val phone: PhoneEntity? = null,

    @Json(name = "name")
    val name: String?
)
