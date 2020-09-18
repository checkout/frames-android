package com.checkout.android_sdk.Models

import com.google.gson.annotations.SerializedName

/**
 * Http request Google Pay object model
 */
class GooglePayModel {

    @SerializedName("signature")
    var signature: String? = null
    @SerializedName("protocolVersion")
    var protocolVersion: String? = null
    @SerializedName("signedMessage")
    var signedMessage: String? = null
}
