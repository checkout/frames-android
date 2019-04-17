package com.checkout.sdk.billingdetails

import java.util.*

open class CountriesManager {
    private val mapCountryToCountryCode = mutableMapOf<String, String>()

    init {
        for (loc in Locale.getAvailableLocales()) {
            val country = loc.displayCountry
            if (country.isNotEmpty()) {
                mapCountryToCountryCode[country] = loc.country
            }
        }
    }

    open fun getSortedCountriesList(): MutableList<String> {
        val keyList = mapCountryToCountryCode.keys.toMutableList()
        keyList.sortWith(String.CASE_INSENSITIVE_ORDER)
        return keyList
    }

    open fun getCountryCode(country: String): String? {
        return mapCountryToCountryCode[country]
    }
}
