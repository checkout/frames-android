package com.checkout.sdk.billingdetails

import com.checkout.sdk.architecture.UiState
import com.checkout.sdk.store.InMemoryStore

data class BillingDetailsUiState(
    val countries: List<String> = emptyList(),
    val position: Int = 0,
    val billingDetailsValidity: BillingDetailsValidity? = null
) : UiState {

    companion object {
        fun create(
            countriesManager: CountriesManager,
            store: InMemoryStore,
            positionZeroString: String
        ): BillingDetailsUiState {
            val countries = countriesManager.getSortedCountriesList()
            countries.add(0, positionZeroString)

            val countryIndex = countries.indexOf(store.billingDetails.country)
            val position = if (countryIndex == -1) 0 else countryIndex

            return BillingDetailsUiState(countries, position)
        }
    }
}
