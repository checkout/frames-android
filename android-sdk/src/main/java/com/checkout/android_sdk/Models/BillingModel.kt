package com.checkout.android_sdk.Models

/**
 * Http request billing details object model
 */
class BillingModel(
    val address_line1: String, val address_line2: String, val zip: String, val country: String,
    val city: String, val state: String
)
