package com.checkout.sdk.cvvinput


data class Cvv(val value: String) {

    companion object {
        val UNKNOWN = Cvv("unknown")
    }
}
