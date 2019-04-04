package com.checkout.sdk.models

/**
 * Http request billing details object model
 */
data class BillingModel(
    val addressOne: String = "",
    val addressTwo: String = "",
    val postcode: String = "",
    val country: String = "",
    val city: String = "",
    val state: String = "",
    val phone: PhoneModel = PhoneModel()
)
