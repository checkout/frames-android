package com.checkout.sdk.request

/**
 * The request model object for the Google Pay tokenisation request
 */
import com.google.gson.annotations.SerializedName


class GooglePayTokenizationRequest(
    @SerializedName("token_data") private val tokenData: TokenData
) {
    @SerializedName("type")
    private val type = "googlepay"

    class TokenData(
        @SerializedName("protocolVersion") private val protocolVersion: String,
        @SerializedName("signature") private val signature: String,
        @SerializedName("signedMessage") private val signedMessage: String
    )
}


