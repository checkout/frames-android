package com.checkout.sdk.billingdetails

import com.checkout.sdk.architecture.UiState
import com.checkout.sdk.store.InMemoryStore
import java.util.*

data class BillingDetailsUiState(val countries: List<String> = emptyList(), val position: Int = 0) : UiState {

    companion object {
        fun create(inMemoryStore: InMemoryStore, positionZeroString: String): BillingDetailsUiState {
            val locale = Locale.getAvailableLocales()
            val countries = ArrayList<String>()
            var country: String

            for (loc in locale) {
                country = loc.displayCountry
                if (country.isNotEmpty() && !countries.contains(country)) {
                    countries.add(country)
                }
            }
            Collections.sort(countries, String.CASE_INSENSITIVE_ORDER)
            countries.add(0, positionZeroString)

            val countryIndex = countries.indexOf(inMemoryStore.billingDetails.country)
            val position = if (countryIndex == -1) 0 else countryIndex

            return BillingDetailsUiState(countries, position)
        }
    }
}
