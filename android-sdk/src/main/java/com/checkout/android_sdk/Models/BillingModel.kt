package com.checkout.android_sdk.Models

/**
 * Http request billing details object model
 */
class BillingModel(
    val addressLine1: String, val addressLine2: String, val postcode: String, val country: String,
    val city: String, val state: String, val phone: PhoneModel
)
