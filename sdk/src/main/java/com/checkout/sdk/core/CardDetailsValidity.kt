package com.checkout.sdk.core


data class CardDetailsValidity(
    val cardNumberValid: Boolean,
    val cvvValid: Boolean,
    val monthValid: Boolean,
    val yearValid: Boolean
) {
    fun areDetailsValid(): Boolean {
        return cardNumberValid && cvvValid && monthValid && yearValid
    }
}
