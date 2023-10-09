package com.checkout.tokenization.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Token details returns as the response to CVVTokenization call.
 */
internal typealias GetCVVTokenDetailsResponse = CVVTokenDetailsResponse

@JsonClass(generateAdapter = true)
internal data class CVVTokenDetailsResponse(
    @Json(name = "type")
    val type: String,

    @Json(name = "token")
    val token: String,

    @Json(name = "expires_on")
    val expiresOn: String,
)
