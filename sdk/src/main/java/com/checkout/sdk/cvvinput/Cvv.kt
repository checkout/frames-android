package com.checkout.sdk.cvvinput

import com.checkout.sdk.utils.CardUtils


data class Cvv(val value: String, val expectedLength: Int) {

    fun isValid(): Boolean {
        return value.trim().length == expectedLength
    }

    companion object {
        val UNKNOWN = Cvv("unknown", CardUtils.Cards.DEFAULT.maxCvvLength)
    }
}
