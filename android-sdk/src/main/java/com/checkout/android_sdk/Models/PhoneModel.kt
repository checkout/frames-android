package com.checkout.android_sdk.Models

import com.google.gson.annotations.SerializedName

/**
 * Http request Phone object model
 */
class PhoneModel(
        @SerializedName("country_code")
        val country_code: String,
        @SerializedName("number")
        val number: String
)
