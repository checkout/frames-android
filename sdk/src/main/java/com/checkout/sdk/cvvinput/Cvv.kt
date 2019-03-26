package com.checkout.sdk.cvvinput

import com.checkout.sdk.core.Card


data class Cvv(val value: String, val expectedLength: Int) {

    fun isValid(): Boolean {
        return value.trim().length == expectedLength
    }

    companion object {
        val UNKNOWN = Cvv("unknown", Card.DEFAULT.maxCvvLength)
    }
}
