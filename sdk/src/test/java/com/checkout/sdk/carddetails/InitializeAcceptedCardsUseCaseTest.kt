package com.checkout.sdk.carddetails

import com.checkout.sdk.FormCustomizer
import com.checkout.sdk.core.Card
import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
class InitializeAcceptedCardsUseCaseTest {

    @Mock
    private lateinit var formCustomizer: FormCustomizer

    private lateinit var useCase: InitializeAcceptedCardsUseCase

    @BeforeEach
    fun onSetup() {
        useCase = InitializeAcceptedCardsUseCase(formCustomizer)
    }

    @Test
    fun `given there are accepted cards in the data store when the use case is executed then we will return those cards`() {
        val expectedCards = listOf(Card.UNIONPAY, Card.AMEX, Card.DINERSCLUB)
        given(formCustomizer.getAcceptedCards()).willReturn(expectedCards)

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
        given(formCustomizer.getAcceptedCards()).willReturn(null)

        val actualCards = useCase.execute()

        assertEquals(expectedCards, actualCards)
    }

}
