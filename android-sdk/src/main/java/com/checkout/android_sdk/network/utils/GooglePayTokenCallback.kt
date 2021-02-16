package com.checkout.android_sdk.network.utils

import com.checkout.android_sdk.Response.GooglePayTokenisationFail
import com.checkout.android_sdk.Response.GooglePayTokenisationResponse
import com.checkout.android_sdk.Response.TokenisationFail
import com.checkout.android_sdk.network.TokenisationRequestListener
import com.google.gson.Gson
import com.google.gson.JsonParseException

internal class GooglePayTokenCallback(
    listener: TokenisationRequestListener<GooglePayTokenisationResponse>,
    val gson: Gson
) : OkHttpTokenCallback<GooglePayTokenisationResponse>(listener) {

    @Throws(JsonParseException::class)
    override fun toSuccessResult(jsonString: String): GooglePayTokenisationResponse {
        return gson.fromJson(jsonString, GooglePayTokenisationResponse::class.java)
    }

    @Throws(JsonParseException::class)
    override fun toFailureResult(jsonString: String): TokenisationFail {
        return gson.fromJson(jsonString, GooglePayTokenisationFail::class.java)
    }
}