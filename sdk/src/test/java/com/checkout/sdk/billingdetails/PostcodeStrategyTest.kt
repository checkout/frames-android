package com.checkout.sdk.billingdetails

import com.checkout.sdk.billingdetails.model.BillingDetail
import com.checkout.sdk.billingdetails.model.BillingDetails
import com.checkout.sdk.billingdetails.strategy.PostcodeStrategy
import com.checkout.sdk.store.InMemoryStore
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class PostcodeStrategyTest {

    private val billingDetails = BillingDetails(postcode = BillingDetail("WR9 3FT"))

    @Mock
    private lateinit var store: InMemoryStore

    @InjectMocks
    private lateinit var strategy: PostcodeStrategy

    @Test
    fun `given store has an initial value when get initial value read it from the store`() {
        val expectedText = billingDetails.postcode.value
        given(store.billingDetails).willReturn(billingDetails)

        val value = strategy.getInitialValue()

        Assert.assertEquals(expectedText, value)
    }

    @Test
    fun `when text changed then store the value as address one`() {
        val expectedText = "something new"
        val expectedBillingDetails = billingDetails.copy(postcode = BillingDetail(expectedText))
        given(store.billingDetails).willReturn(billingDetails)
        strategy.textChanged(expectedText)
        then(store).should().billingDetails = expectedBillingDetails
    }

    @Test
    fun `given text length shorter than minimum when focus changed then error shown is true`() {
        val (shortText, expectedResult) = Pair("ab", true)
        given(store.billingDetails)
            .willReturn(BillingDetails(postcode = BillingDetail(shortText)))

        val result = strategy.focusChanged(false)
        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `given text length longer than minimum when focus changed then error shown is false`() {
        val (shortText, expectedResult) = Pair("abcd", false)
        given(store.billingDetails)
            .willReturn(BillingDetails(postcode = BillingDetail(shortText)))

        val result = strategy.focusChanged(false)
        assertEquals(expectedResult, result)
    }

    @Test
    fun `when reset then stored address one is an empty string`() {
        val expectedText = ""
        val expectedBillingDetails = billingDetails.copy(postcode = BillingDetail(expectedText))
        given(store.billingDetails).willReturn(billingDetails)
        strategy.reset()
        then(store).should().billingDetails = expectedBillingDetails
    }
}
