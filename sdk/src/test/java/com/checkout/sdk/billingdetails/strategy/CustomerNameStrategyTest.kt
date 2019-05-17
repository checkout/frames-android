package com.checkout.sdk.billingdetails.strategy

import com.checkout.sdk.billingdetails.model.BillingDetail
import com.checkout.sdk.store.InMemoryStore
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class BillingDetailStrategyTest {

    @Mock
    private lateinit var store: InMemoryStore

    @InjectMocks
    private lateinit var strategy: CustomerNameStrategy

    @Test
    fun `given store has an initial value when get initial value read it from the store`() {
        val expectedBillingDetail = BillingDetail("something")
        given(store.customerName).willReturn(expectedBillingDetail)

        val value = strategy.getInitialValue()

        assertEquals(expectedBillingDetail.value, value)
    }

    @Test
    fun `when text changed then store the value as address one`() {
        val expectedBillingDetail = BillingDetail("something")
        given(store.customerName).willReturn(expectedBillingDetail)

        strategy.textChanged(expectedBillingDetail.value)

        then(store).should().customerName = expectedBillingDetail
    }

    @Test
    fun `given text length shorter than minimum when focus changed then error shown is true`() {
        val expectedBillingDetail = BillingDetail("ab")
        given(store.customerName).willReturn(expectedBillingDetail)
        val result = strategy.focusChanged( false)
        assertTrue(result)
    }

    @Test
    fun `given text length longer than minimum when focus changed then error shown is false`() {
        val (shortText, expectedResult) = Pair("abcd", false)
        given(store.customerName).willReturn(BillingDetail(shortText))

        val result = strategy.focusChanged(false)
        assertEquals(expectedResult, result)
    }

    @Test
    fun `when reset then stored address one is an empty string`() {
        val expectedBillingDetail = BillingDetail("")
        strategy.reset()
        then(store).should().customerName = expectedBillingDetail
    }
}
