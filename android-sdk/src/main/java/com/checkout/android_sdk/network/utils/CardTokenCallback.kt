package com.checkout.android_sdk.network.utils

import com.checkout.android_sdk.Response.CardTokenisationFail
import com.checkout.android_sdk.Response.CardTokenisationResponse
import com.checkout.android_sdk.Response.TokenisationFail
import com.checkout.android_sdk.network.TokenisationRequestListener
import com.google.gson.Gson
import com.google.gson.JsonParseException

internal class CardTokenCallback(
    listener: TokenisationRequestListener<CardTokenisationResponse>,
    val gson: Gson
) : OkHttpTokenCallback<CardTokenisationResponse>(listener) {

    @Throws(JsonParseException::class)
    override fun toSuccessResult(jsonString: String): CardTokenisationResponse {
        return gson.fromJson(jsonString, CardTokenisationResponse::class.java)
    }

    @Throws(JsonParseException::class)
    override fun toFailureResult(jsonString: String): TokenisationFail {
        return gson.fromJson(jsonString, CardTokenisationFail::class.java)
    }
}