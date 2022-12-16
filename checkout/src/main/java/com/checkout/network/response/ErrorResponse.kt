package com.checkout.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data class used to represent an error / failure reported by the server.
 */
@JsonClass(generateAdapter = true)
internal data class ErrorResponse(
    @Json(name = "request_id")
    val requestId: String?,

    @Json(name = "error_type")
    val errorType: String?,

    @Json(name = "error_codes")
    val errorCodes: List<String>?
)
