package com.checkout.sdk.billingdetails.strategy

import com.checkout.sdk.billingdetails.model.BillingDetail
import com.checkout.sdk.billingdetails.model.BillingDetails
import com.checkout.sdk.billingdetails.model.CityDetail
import com.checkout.sdk.models.PhoneModel
import com.checkout.sdk.store.InMemoryStore
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
class PhoneNumberStrategyTest {

    @Mock
    private lateinit var store: InMemoryStore

    @Mock
    private lateinit var listener: InMemoryStore.PhoneModelListener

    @InjectMocks
    private lateinit var strategy: PhoneNumberStrategy

    private val countryCode = "+399"
    private val phoneNumber = "3270832"
    private val phoneModel = PhoneModel(countryCode, phoneNumber)
    private val billingDetails = BillingDetails(
        BillingDetail("addressOne"),
        BillingDetail("addressTwo"),
        CityDetail("city"),
        BillingDetail("state"),
        BillingDetail("postcode"),
        "country",
        phoneModel
    )

    @Test
    fun `given store has a phone model then the initial value is the country code concatenated with the phone number`() {
        given(store.billingDetails).willReturn(billingDetails)
        val expectedValue = "$countryCode $phoneNumber"

        val value = strategy.getInitialValue()

        assertEquals(expectedValue, value)
    }

    @Test
    fun `when we listen for repository change then we listen for country code change on the store`() {
        strategy.listenForRepositoryChange(listener)

        then(store).should().listenForCountryCodeChange(listener)
    }

    @Test
    fun `when text changes we store the new phone model in the repository`() {
        given(store.billingDetails).willReturn(billingDetails)
        val (countryCode, newText) = Pair("+399", "32708329")
        val expectedBillingDetails = billingDetails.copy(phone = PhoneModel(countryCode, newText))

        strategy.textChanged("$countryCode $newText")

        then(store).should().billingDetails = expectedBillingDetails
    }

    @Test
    fun `when we lose focus we show an error if the phone number is too short`() {
        val shortBillingDetails = billingDetails.copy(phone = PhoneModel("+", "12"))
        given(store.billingDetails).willReturn(shortBillingDetails)

        val hasError = strategy.focusChanged(false)

        assertTrue(hasError)
    }

    @Test
    fun `when we lose focus we don't show an error if the phone number is long enough`() {
        given(store.billingDetails).willReturn(billingDetails)

        val hasError = strategy.focusChanged(false)

        assertFalse(hasError)
    }

    @Test
    fun `when we reset we have a phone model with empty strings`() {
        val expectedPhoneModel = PhoneModel()
        given(store.billingDetails).willReturn(billingDetails)

        strategy.reset()

        then(store).should().billingDetails = billingDetails.copy(phone = expectedPhoneModel)
    }
}
