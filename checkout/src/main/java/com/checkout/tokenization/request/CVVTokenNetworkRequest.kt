package com.checkout.tokenization.request

import com.checkout.tokenization.entity.TokenDataEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class CVVTokenNetworkRequest(
    @Json(name = "type")
    val type: String,

    @Json(name = "token_data")
    val tokenDataEntity: TokenDataEntity
)
