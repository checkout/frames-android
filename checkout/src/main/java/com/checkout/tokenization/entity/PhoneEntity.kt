package com.checkout.tokenization.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * A representation of a [PhoneEntity]
 */
@JsonClass(generateAdapter = true)
internal data class PhoneEntity internal constructor(
    /**
     * The phone number.
     */
    @Json(name = "number")
    val number: String,

    /**
     * The international country calling code.
     */
    @Json(name = "country_code")
    val countryCode: String?,
)
