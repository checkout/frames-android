package com.checkout.sdk.core


data class RequestValidity(
    val cardNumberValid: Boolean,
    val cvvValid: Boolean,
    val monthValid: Boolean,
    val yearValid: Boolean
) {
    fun isRequestValid(): Boolean {
        return cardNumberValid && cvvValid && monthValid && yearValid
    }
}
