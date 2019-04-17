package com.checkout.sdk.models

/**
 * Http request Phone object model
 */
data class PhoneModel(
    val countryCode: String = "",
    val number: String = "") {

    fun isValid(): Boolean {
        return countryCode.length + number.length > 2
    }
}
