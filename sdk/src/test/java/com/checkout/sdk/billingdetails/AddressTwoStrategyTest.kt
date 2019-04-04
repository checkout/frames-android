package com.checkout.sdk.billingdetails

import com.checkout.sdk.models.BillingModel
import com.checkout.sdk.store.InMemoryStore
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class AddressTwoStrategyTest {

    private val billingDetails = BillingModel(addressTwo = "Barnstorm Way")

    @Mock
    private lateinit var store: InMemoryStore

    @InjectMocks
    private lateinit var strategy: AddressTwoStrategy

    @Test
    fun `given store has an initial value when get initial value read it from the store`() {
        val expectedText = billingDetails.addressTwo
        BDDMockito.given(store.billingDetails).willReturn(billingDetails)

        val value = strategy.getInitialValue()

        Assert.assertEquals(expectedText, value)
    }

    @Test
    fun `when text changed then store the value as address one`() {
        val expectedText = "something new"
        val expectedBillingDetails = billingDetails.copy(addressTwo = expectedText)
        BDDMockito.given(store.billingDetails).willReturn(billingDetails)
        strategy.textChanged(expectedText)
        BDDMockito.then(store).should().billingDetails = expectedBillingDetails
    }

    @Test
    fun `given text length shorter than minimum when focus changed then error shown is true`() {
        val (shortText, expectedResult) = Pair("ab", true)
        val result = strategy.focusChanged(shortText, false)
        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `given text length longer than minimum when focus changed then error shown is false`() {
        val (shortText, expectedResult) = Pair("abcd", false)
        val result = strategy.focusChanged(shortText, false)
        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `when reset then stored address one is an empty string`() {
        val expectedText = ""
        val expectedBillingDetails = billingDetails.copy(addressTwo = expectedText)
        BDDMockito.given(store.billingDetails).willReturn(billingDetails)
        strategy.reset()
        BDDMockito.then(store).should().billingDetails = expectedBillingDetails
    }
}
