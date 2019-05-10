package com.checkout.sdk.request

/**
 * The request model object for the Google Pay tokenisation request
 */
import com.google.gson.annotations.SerializedName


class GooglePayTokenizationRequest(protocolVersion: String,
                                   signature: String,
                                   signedMessage: String
) {
    @SerializedName("type")
    private val type = "googlepay"
    @SerializedName("token_data")
    private val tokenData: TokenData = TokenData(protocolVersion, signature, signedMessage)

    private class TokenData(
        @SerializedName("protocolVersion") private val protocolVersion: String,
        @SerializedName("signature") private val signature: String,
        @SerializedName("signedMessage") private val signedMessage: String
    )
}


