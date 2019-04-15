package com.checkout.sdk.billingdetails

import com.checkout.sdk.billingdetails.model.BillingDetails
import com.google.gson.annotations.SerializedName

data class NetworkBillingModel(
    @SerializedName("addressLine1") val addressOne: String,
    @SerializedName("addressLine2") val addressTwo: String,
    @SerializedName("postcode") val postcode: String,
    @SerializedName("country") val country: String,
    @SerializedName("city") val city: String,
    @SerializedName("state") val state: String,
    @SerializedName("phone") val phone: String
) {
    companion object {
        fun from(billingDetails: BillingDetails): NetworkBillingModel {
            return NetworkBillingModel(
                billingDetails.addressOne.value,
                billingDetails.addressTwo.value,
                billingDetails.postcode.value,
                billingDetails.country,
                billingDetails.city.value,
                billingDetails.state.value,
                billingDetails.phone.countryCode + billingDetails.phone.number
            )
        }
    }
}
