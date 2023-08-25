package com.checkout.tokenization.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * A representation of a [AddressEntity]
 */
@JsonClass(generateAdapter = true)
internal data class AddressEntity internal constructor(

    /**
     * Address line 1 (Street address/PO Box/Company name)
     */
    @Json(name = "address_line1")
    val addressLine1: String,

    /**
     * Address line 2 (Apartment/Suite/Unit/Building)
     */
    @Json(name = "address_line2")
    val addressLine2: String,

    /**
     * City/District/Suburb/Town/Village
     */
    @Json(name = "city")
    val city: String,

    /**
     * State/County/Province/Region
     */
    @Json(name = "state")
    val state: String,

    /**
     * ZIP or postal code
     */
    @Json(name = "zip")
    val zip: String,

    /**
     * Billing address country code (iso3166Alpha2)
     */
    @Json(name = "country")
    val country: String
)
