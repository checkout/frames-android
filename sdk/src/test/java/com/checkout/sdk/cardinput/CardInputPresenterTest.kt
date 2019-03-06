package com.checkout.sdk.cardinput

import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.utils.CardUtils
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.Mockito.reset
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CardInputPresenterTest {

    @Mock
    private lateinit var viewMock: MvpView<CardInputUiState>

    @Mock
    private lateinit var cardInputUseCase: CardInputUseCase

    @Mock
    private lateinit var cardFocusUseCase: CardFocusUseCase

    private lateinit var initialState: CardInputUiState

    private lateinit var presenter: CardInputPresenter

    @Before
    fun onSetup() {
        initialState = CardInputUiState("1234", CardUtils.Cards.JCB, false, false)
        presenter = CardInputPresenter(initialState)
        presenter.start(viewMock)
        reset(viewMock)
    }

    @Test
    fun `given text changed then use case result should be applied to view state`() {
        val expectedState = CardInputUiState(
            "4139578",
            CardUtils.Cards.DISCOVER,
            true,
            true
        )
        given(cardInputUseCase.execute()).willReturn(
            CardInputUseCase.CardInputResult(
                expectedState.cardNumber,
                expectedState.cardType,
                expectedState.inputFinished,
                expectedState.showCardError
            )
        )

        presenter.textChanged(cardInputUseCase)

        then(viewMock).should().onStateUpdated(expectedState)
    }

    @Test
    fun `given card input looses focus with invalid number entered then should show error`() {
        val expectedError = true
        given(cardFocusUseCase.execute()).willReturn(expectedError)

        val cardInputUiState = initialState.copy(showCardError = expectedError)

        presenter.focusChanged(cardFocusUseCase)

        then(viewMock).should().onStateUpdated(cardInputUiState)
    }
}
