package com.checkout.sdk.billingdetails.model


data class CityDetail(val value: String = "") {

    fun isValid(): Boolean {
        return value.length >= MINIMUM_CITY_NAME_LENGTH
    }

    companion object {
        private const val MINIMUM_CITY_NAME_LENGTH = 2
    }
}
