package com.checkout.android_sdk.Models

import com.google.gson.annotations.SerializedName

/**
 * Http request card details object model
 */
class CardModel {
    @SerializedName("expiryMonth")
    val expiryMonth: String? = null
    @SerializedName("expiryYear")
    val expiryYear: String? = null
    @SerializedName("billingDetails")
    val billingDetails: BillingModel? = null
    @SerializedName("id")
    val id: String? = null
    @SerializedName("last4")
    val last4: String? = null
    @SerializedName("bin")
    val bin: String? = null
    @SerializedName("paymentMethod")
    val paymentMethod: String? = null
    @SerializedName("name")
    val name: String? = null
}
