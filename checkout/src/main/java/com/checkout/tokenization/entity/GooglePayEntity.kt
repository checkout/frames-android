package com.checkout.tokenization.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Http request Google Pay object model
 */
@JsonClass(generateAdapter = true)
internal data class GooglePayEntity(

    @Json(name = "signature")
    var signature: String? = null,

    @Json(name = "protocolVersion")
    var protocolVersion: String? = null,

    @Json(name = "signedMessage")
    var signedMessage: String? = null
)
