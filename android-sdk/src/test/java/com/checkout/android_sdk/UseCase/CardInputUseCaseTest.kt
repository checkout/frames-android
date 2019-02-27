package com.checkout.android_sdk.UseCase

import android.text.Editable
import com.checkout.android_sdk.Store.DataStore
import com.checkout.android_sdk.Utils.CardUtils
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CardInputUseCaseTest {

    // TODO: I think DataStore will disappear, or signigicantly change, so I am not going to
    // TODO: write any test assertions for it
    @Mock
    private lateinit var dataStore: DataStore

    @Mock
    private lateinit var callbackMock: CardInputUseCase.Callback

    @Mock
    lateinit var editable: Editable

    @Test
    fun `given insufficient digits of card number will get default card and input not finished`() {
        val cardNumber = "1234"
        given(editable.toString()).willReturn(cardNumber)

        CardInputUseCase(editable, dataStore, callbackMock).execute()

        val expectedResult = CardInputUseCase.CardInputResult(cardNumber, CardUtils.Cards.DEFAULT, false)
        then(callbackMock).should().onCardInputResult(expectedResult)
        assertEquals(cardNumber, editable.toString())
    }

    @Test
    fun `given sufficient digits of visa card number will get visa as type and input finished`() {
        val spacedVisaNumber = "4242 4242 4242 4242"
        val nonSpacedVisaNumber = "4242424242424242"
        given(editable.toString()).willReturn(spacedVisaNumber)

        CardInputUseCase(editable, dataStore, callbackMock).execute()

        val expectedResult = CardInputUseCase.CardInputResult(nonSpacedVisaNumber, CardUtils.Cards.VISA, true)
        then(callbackMock).should().onCardInputResult(expectedResult)
        assertEquals(spacedVisaNumber, editable.toString())
    }

    @Test
    fun `given space should be added to a card number then card result is correct and space is added`() {
        val visaNumberToBeSpaced = "42424"
        val visaNumberSpaced = "4242 4"
        given(editable.toString()).willReturn(visaNumberToBeSpaced)

        CardInputUseCase(editable, dataStore, callbackMock).execute()

        val expectedResult = CardInputUseCase.CardInputResult(visaNumberToBeSpaced, CardUtils.Cards.VISA, false)
        then(callbackMock).should().onCardInputResult(expectedResult)
        then(editable).should().replace(0, visaNumberToBeSpaced.length, visaNumberSpaced)
    }

}
