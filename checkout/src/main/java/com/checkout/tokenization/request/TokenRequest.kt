package com.checkout.tokenization.request

import com.checkout.tokenization.entity.AddressEntity
import com.checkout.tokenization.entity.PhoneEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class TokenRequest(
    @Json(name = "type")
    val type: String,

    @Json(name = "number")
    val number: String,

    @Json(name = "expiry_month")
    val expiryMonth: String,

    @Json(name = "expiry_year")
    val expiryYear: String,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "cvv")
    val cvv: String? = null,

    @Json(name = "billing_address")
    val billingAddress: AddressEntity? = null,

    @Json(name = "phone")
    val phone: PhoneEntity? = null,
)
