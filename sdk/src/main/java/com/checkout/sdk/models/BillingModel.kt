package com.checkout.sdk.models

import com.checkout.sdk.billingdetails.model.BillingDetail

/**
 * Http request billing details object model
 */
data class BillingModel(
    val addressOne: BillingDetail = BillingDetail(),
    val addressTwo: BillingDetail = BillingDetail(),
    val postcode: String = "",
    val country: String = "",
    val city: String = "",
    val state: String = "",
    val phone: PhoneModel = PhoneModel()
)
