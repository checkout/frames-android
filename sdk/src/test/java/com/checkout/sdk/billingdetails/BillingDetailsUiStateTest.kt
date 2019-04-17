package com.checkout.sdk.billingdetails

import com.checkout.sdk.billingdetails.model.BillingDetails
import com.checkout.sdk.store.InMemoryStore
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BillingDetailsUiStateTest {

    @Mock
    private lateinit var countriesManager: CountriesManager

    @Mock
    private lateinit var store: InMemoryStore

    @Test
    fun `given a store with a selected country and a CountriesManager with a map of counties we get a list with the a specified string at the first position and the position of the country in the list`() {
        val positionZeroString = "You will pick a country from the list"
        val selectedCountry = "Bolivia"
        val expectedList = mutableListOf("Algeria", selectedCountry, "Canada")
        val expectedUiState = BillingDetailsUiState(expectedList, 2, null)
        given(store.billingDetails).willReturn(BillingDetails(country = selectedCountry))
        given(countriesManager.getSortedCountriesList()).willReturn(expectedList)

        val uiState = BillingDetailsUiState.create(countriesManager, store, positionZeroString)

        assertEquals(expectedUiState, uiState)
    }
}
