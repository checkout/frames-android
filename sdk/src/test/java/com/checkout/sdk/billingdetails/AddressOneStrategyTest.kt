package com.checkout.sdk.billingdetails

import com.checkout.sdk.store.InMemoryStore
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class AddressOneStrategyTest {

    @Mock
    private lateinit var store: InMemoryStore

    @InjectMocks
    private lateinit var addressOneStrategy: AddressOneStrategy

    @Test
    fun `when text changed then store the value as address one`() {
        val expectedText = "something"
        addressOneStrategy.textChanged(expectedText)
        then(store).should().addressOne = expectedText
    }

    @Test
    fun `given text length shorter than minimum when focus changed then error shown is true`() {
        val (shortText, expectedResult) = Pair("ab", true)
        val result = addressOneStrategy.focusChanged(shortText, false)
        assertEquals(expectedResult, result)
    }

    @Test
    fun `given text length longer than minimum when focus changed then error shown is false`() {
        val (shortText, expectedResult) = Pair("abcd", false)
        val result = addressOneStrategy.focusChanged(shortText, false)
        assertEquals(expectedResult, result)
    }

    @Test
    fun `when reset then stored address one is an empty string`() {
        val expectedText = ""
        addressOneStrategy.reset()
        then(store).should().addressOne = expectedText
    }
}
