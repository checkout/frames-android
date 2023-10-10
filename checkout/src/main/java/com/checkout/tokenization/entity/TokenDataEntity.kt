package com.checkout.tokenization.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class TokenDataEntity internal constructor(
    @Json(name = "cvv")
    val cvv: String = "",
)
