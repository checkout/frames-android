package com.checkout.android_sdk.Models

import com.google.gson.annotations.SerializedName

/**
 * Http request billing details object model
 */
class BillingModel(
        @SerializedName("address_line1")
        val address_line1: String,
        @SerializedName("address_line2")
        val address_line2: String,
        @SerializedName("zip")
        val zip: String,
        @SerializedName("country")
        val country: String,
        @SerializedName("city")
        val city: String,
        @SerializedName("state")
        val state: String
)
