package com.checkout.sdk.billingdetails

import com.checkout.sdk.models.BillingModel
import com.checkout.sdk.store.InMemoryStore
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class AddressOneStrategyTest {

    private val billingDetails = BillingModel(addressOne = "27 Parch St")

    @Mock
    private lateinit var store: InMemoryStore

    @InjectMocks
    private lateinit var strategy: AddressOneStrategy

    @Test
    fun `given store has an initial value when get initial value read it from the store`() {
        val expectedText = billingDetails.addressOne
        given(store.billingDetails).willReturn(billingDetails)

        val value = strategy.getInitialValue()

        assertEquals(expectedText, value)
    }

    @Test
    fun `when text changed then store the value as address one`() {
        val expectedText = "something new"
        val expectedBillingDetails = billingDetails.copy(addressOne = expectedText)
        given(store.billingDetails).willReturn(billingDetails)
        strategy.textChanged(expectedText)
        then(store).should().billingDetails = expectedBillingDetails
    }

    @Test
    fun `given text length shorter than minimum when focus changed then error shown is true`() {
        val (shortText, expectedResult) = Pair("ab", true)
        val result = strategy.focusChanged(shortText, false)
        assertEquals(expectedResult, result)
    }

    @Test
    fun `given text length longer than minimum when focus changed then error shown is false`() {
        val (shortText, expectedResult) = Pair("abcd", false)
        val result = strategy.focusChanged(shortText, false)
        assertEquals(expectedResult, result)
    }

    @Test
    fun `when reset then stored address one is an empty string`() {
        val expectedText = ""
        strategy.reset()
        then(store).should().addressOne = expectedText
    }
}
