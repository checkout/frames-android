package com.checkout.sdk.cardinput

import android.text.Editable
import com.checkout.sdk.cvvinput.Cvv
import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.utils.CardUtils
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CardInputUseCaseTest {

    @Mock
    private lateinit var inMemoryStore: InMemoryStore

    @Mock
    private lateinit var editable: Editable

    private val initialCvv = Cvv("888", 3)

    @Test
    fun `given insufficient digits of card number will get default card and input not finished`() {
        val cardNumber = CardNumber("1234")
        given(editable.toString()).willReturn(cardNumber.value)
        given(inMemoryStore.cvv).willReturn(initialCvv)

        val cardInputType = CardInputUseCase(editable, inMemoryStore).execute()

        val expectedType = CardUtils.Card.DEFAULT
        assertEquals(expectedType, cardInputType)
        assertEquals(cardNumber.value, editable.toString())
        then(inMemoryStore).should().cardNumber = cardNumber
    }

    @Test
    fun `given sufficient digits of visa card number will get visa as type and input finished`() {
        val spacedVisaNumber = "4242 4242 4242 4242"
        val nonSpacedVisaNumber = CardNumber("4242424242424242")
        given(editable.toString()).willReturn(spacedVisaNumber)
        given(inMemoryStore.cvv).willReturn(initialCvv)

        val cardType = CardInputUseCase(editable, inMemoryStore).execute()

        val expectedType = CardUtils.Card.VISA
        assertEquals(expectedType, cardType)
        assertEquals(spacedVisaNumber, editable.toString())
        then(inMemoryStore).should().cardNumber = nonSpacedVisaNumber
    }

    @Test
    fun `given space should be added to a card number then card result is correct and space is added`() {
        val visaNumberToBeSpaced = CardNumber("42424")
        val visaNumberSpaced = "4242 4"
        given(editable.toString()).willReturn(visaNumberToBeSpaced.value)
        given(inMemoryStore.cvv).willReturn(initialCvv)

        val cardType = CardInputUseCase(editable, inMemoryStore).execute()

        val expectedType = CardUtils.Card.VISA
        assertEquals(expectedType, cardType)
        then(editable).should().replace(0, visaNumberToBeSpaced.value.length, visaNumberSpaced)
        then(inMemoryStore).should().cardNumber = visaNumberToBeSpaced
    }

}
