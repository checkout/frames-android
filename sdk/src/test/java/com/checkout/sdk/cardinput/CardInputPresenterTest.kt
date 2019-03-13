package com.checkout.sdk.cardinput

import android.text.Editable
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

    @Mock
    private lateinit var editable: Editable


    private lateinit var initialState: CardInputUiState

    private lateinit var presenter: CardInputPresenter

    @Before
    fun onSetup() {
        initialState = CardInputUiState("1234", CardUtils.Cards.JCB, false)
        presenter = CardInputPresenter(initialState)
        presenter.start(viewMock)
        reset(viewMock)
    }

    @Test
    fun `given text changed then use case result should be applied to view state`() {
        val initialState = CardInputUiState(
            "4139578",
            CardUtils.Cards.DISCOVER,
            false
        )
        given(cardInputUseCase.execute()).willReturn(initialState.cardType)
        given(cardInputUseCase.editableText).willReturn(editable)
        val expectedCardNumber = "4139 578"
        given(editable.toString()).willReturn(expectedCardNumber)
        val expectedState = initialState.copy(cardNumber = expectedCardNumber)

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

    @Test
    fun `given presenter is started when show error called then the state is the same except with showError being updated`() {
        val expectedError = true
        presenter.showError(expectedError)

        then(viewMock).should().onStateUpdated(initialState.copy(showCardError = expectedError))
    }
}
