package com.checkout.sdk.billingdetails.strategy

import com.checkout.sdk.billingdetails.model.BillingDetail
import com.checkout.sdk.billingdetails.model.BillingDetails
import com.checkout.sdk.store.InMemoryStore
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
class AddressTwoStrategyTest {

    private val billingDetails =
        BillingDetails(addressTwo = BillingDetail("Barnstorm Way"))

    @Mock
    private lateinit var store: InMemoryStore

    @InjectMocks
    private lateinit var strategy: AddressTwoStrategy

    @Test
    fun `given store has an initial value when get initial value read it from the store`() {
        val expectedBillingValue = billingDetails.addressTwo.value
        given(store.billingDetails).willReturn(billingDetails)

        val value = strategy.getInitialValue()

        assertEquals(expectedBillingValue, value)
    }

    @Test
    fun `when text changed then store the value as address one`() {
        val expectedText = "something new"
        val expectedBillingDetails = billingDetails.copy(addressTwo = BillingDetail(expectedText))
        given(store.billingDetails).willReturn(billingDetails)

        strategy.textChanged(expectedText)

        then(store).should().billingDetails = expectedBillingDetails
    }

    @Test
    fun `given text length shorter than minimum when focus changed then error shown is true`() {
        val (shortText, expectedResult) = Pair("ab", true)
        given(store.billingDetails).willReturn(
            BillingDetails(
                addressTwo = BillingDetail(value = shortText)
            )
        )

        val result = strategy.focusChanged(false)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `given text length longer than minimum when focus changed then error shown is false`() {
        val (shortText, expectedResult) = Pair("abcd", false)
        given(store.billingDetails).willReturn(
            BillingDetails(
                addressTwo = BillingDetail(value = shortText)
            )
        )

        val result = strategy.focusChanged(false)
        assertEquals(expectedResult, result)
    }

    @Test
    fun `when reset then stored address one is an empty string`() {
        val expectedText = ""
        val expectedBillingDetails = billingDetails.copy(addressTwo = BillingDetail(expectedText))
        given(store.billingDetails).willReturn(billingDetails)
        strategy.reset()
        then(store).should().billingDetails = expectedBillingDetails
    }
}
