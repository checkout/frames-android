package com.checkout.sdk.date


data class Year(val year: Int) {

    fun isKnown(): Boolean {
        return this != UNKNOWN
    }

    companion object {
        val UNKNOWN = Year(-1)
    }
}
