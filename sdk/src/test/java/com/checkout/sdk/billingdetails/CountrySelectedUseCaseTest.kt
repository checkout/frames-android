package com.checkout.sdk.billingdetails

import com.checkout.sdk.billingdetails.model.BillingDetail
import com.checkout.sdk.billingdetails.model.BillingDetails
import com.checkout.sdk.billingdetails.model.CityDetail
import com.checkout.sdk.models.PhoneModel
import com.checkout.sdk.store.InMemoryStore
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CountrySelectedUseCaseTest {

    private val position = 2

    private val countries = listOf("Venezuala", "Zambia", "Sri Lanka")

    private val billingDetails = BillingDetails(
        BillingDetail("add1"),
        BillingDetail("add2"),
        CityDetail("city"),
        BillingDetail("state"),
        BillingDetail("postcode"),
        "country",
        PhoneModel("+112", "9678134")
    )

    @Mock
    private lateinit var store: InMemoryStore

    @Mock
    private lateinit var countriesManager: CountriesManager

    private lateinit var countrySelectedUseCase: CountrySelectedUseCase

    @Before
    fun setup() {
        countrySelectedUseCase = CountrySelectedUseCase(countriesManager, store, position, countries)
    }

    @Test
    fun `when we have a country and a prefix we update the billing details and the phone model`() {
        val expectedCountry = "Sri Lanka"
        given(store.billingDetails).willReturn(billingDetails)
        given(countriesManager.getCountryCode(expectedCountry)).willReturn("LK")

        countrySelectedUseCase.execute()

        then(store).should().billingDetails = billingDetails.copy(country = expectedCountry)
    }

}
