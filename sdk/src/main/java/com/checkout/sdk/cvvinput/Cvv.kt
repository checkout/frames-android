package com.checkout.sdk.cvvinput


data class Cvv(val value: String, val expectedLength: Int) {

    fun isValid(): Boolean {
        return value.trim().length == expectedLength
    }

    companion object {
        val UNKNOWN = Cvv("unknown", -1)
    }
}
