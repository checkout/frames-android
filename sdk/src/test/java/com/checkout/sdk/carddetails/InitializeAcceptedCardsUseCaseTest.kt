package com.checkout.sdk.carddetails

import com.checkout.sdk.core.Card
import com.checkout.sdk.store.DataStore
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class InitializeAcceptedCardsUseCaseTest {

    @Mock
    private lateinit var dataStore: DataStore

    private lateinit var useCase: InitializeAcceptedCardsUseCase

    @Before
    fun onSetup() {
        useCase = InitializeAcceptedCardsUseCase(dataStore)
    }

    @Test
    fun `given there are accepted cards in the data store when the use case is executed then we will return those cards`() {
        val expectedCards = listOf(Card.UNIONPAY, Card.AMEX, Card.DINERSCLUB)
        given(dataStore.acceptedCards).willReturn(expectedCards)

        val actualCards = useCase.execute()

        assertEquals(expectedCards, actualCards)
    }

    @Test
    fun `given there are no accepted cards in the data store when the use case is executed then we will return all the cards from the cards enum excluding the default card`() {
        val expectedCards = listOf(
            Card.VISA,
            Card.AMEX,
            Card.DISCOVER,
            Card.UNIONPAY,
            Card.JCB,
            Card.DINERSCLUB,
            Card.MASTERCARD,
            Card.MAESTRO
        )
        given(dataStore.acceptedCards).willReturn(null)

        val actualCards = useCase.execute()

        assertEquals(expectedCards, actualCards)
    }

}
