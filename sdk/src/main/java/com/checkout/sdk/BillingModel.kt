package com.checkout.sdk

import com.checkout.sdk.models.PhoneModel


class BillingModel(
    val addressOne: String = "",
    val addressTwo: String = "",
    val city: String = "",
    val country: String = "",
    val postcode: String = "",
    val state: String = "",
    val phone: PhoneModel = PhoneModel())
