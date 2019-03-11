package com.checkout.sdk.date


data class Year(val year: Int) {

    fun isKnown(): Boolean {
        return this.year != UNKNOWN
    }

    companion object {
        const val UNKNOWN = -1
    }
}
