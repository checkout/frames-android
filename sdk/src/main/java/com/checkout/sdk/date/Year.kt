package com.checkout.sdk.date


data class Year(val value: Int) {

    fun isKnown(): Boolean {
        return this != UNKNOWN
    }

    companion object {
        val UNKNOWN = Year(-1)
    }
}
