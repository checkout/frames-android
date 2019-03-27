package com.checkout.sdk.carddetails

import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.core.Card
import com.checkout.sdk.core.CardDetailsValidity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.Mockito.reset
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CardDetailsPresenterTest {

    @Mock
    private lateinit var view: MvpView<CardDetailsUiState>

    @Mock
    private lateinit var payButtonClickedUseCase: PayButtonClickedUseCase

    @Mock
    private lateinit var initializeAcceptedCardsUseCase: InitializeAcceptedCardsUseCase

    @Mock
    private lateinit var updateBillingSpinnerUseCase: UpdateBillingSpinnerUseCase


    private lateinit var initialState: CardDetailsUiState

    private lateinit var presenter: CardDetailsPresenter

    @Before
    fun onSetup() {
        initialState = CardDetailsUiState()
        presenter = CardDetailsPresenter(initialState)
        presenter.start(view)
        reset(view)
    }

    @Test
    fun `given PayButtonClickedUseCase will return CardDetailsValidity then the use case should be executed and the view will be updated with the new validity`() {
        val expectedValidity = CardDetailsValidity(true, true, true, true)
        val expectedState = initialState.copy(cardDetailsValidity = expectedValidity)
        given(payButtonClickedUseCase.execute()).willReturn(expectedValidity)

        presenter.payButtonClicked(payButtonClickedUseCase)

        then(view).should().onStateUpdated(expectedState)
    }

    @Test
    fun `when show progress pressed then view will have a state with show progress as true`() {
        val expectedProgress = true
        val expectedUiState = initialState.copy(inProgress = expectedProgress)

        presenter.showProgress(expectedProgress)

        then(view).should().onStateUpdated(expectedUiState)
    }

    @Test
    fun `given UI state has uninitialized Accepted Cards and AcceptedCardsUseCase will give three cards then UI State should be updated with those cards`() {
        val expectedCards = listOf(Card.JCB, Card.MASTERCARD, Card.DISCOVER)
        given(initializeAcceptedCardsUseCase.execute()).willReturn(expectedCards)
        val expectedUiState = initialState.copy(acceptedCards = expectedCards)

        presenter.initializeAcceptedCards(initializeAcceptedCardsUseCase)

        then(view).should().onStateUpdated(expectedUiState)
    }

    @Test
    fun `given UpdateBillingSpinnerUseCase will return three billing strings then uiState will have spinnerStrings with those values`() {
        val expectedStrings = listOf("Edit", "Add", "Remove")
        given(updateBillingSpinnerUseCase.execute()).willReturn(expectedStrings)
        val expectedUiState = initialState.copy(spinnerStrings = expectedStrings)

        presenter.updateBillingSpinner(updateBillingSpinnerUseCase)

        then(view).should().onStateUpdated(expectedUiState)
    }
}
