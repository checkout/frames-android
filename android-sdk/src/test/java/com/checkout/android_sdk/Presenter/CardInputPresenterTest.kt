package com.checkout.android_sdk.Presenter

import android.text.Editable
import com.checkout.android_sdk.Store.DataStore
import com.checkout.android_sdk.Utils.CardUtils
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
    private lateinit var dataStore: DataStore

    @Mock
    private lateinit var viewMock: CardInputPresenter.CardInputView

    @Mock
    private lateinit var editable: Editable

    private lateinit var initialState: CardInputPresenter.CardInputUiState

    private lateinit var presenter: CardInputPresenter

    @Before
    fun onSetup() {
        initialState =
                CardInputPresenter.CardInputUiState("1234", CardUtils.Cards.JCB, false, false)
        presenter = CardInputPresenter(dataStore, initialState)
    }

    @Test
    fun `given presenter is started then default state should be set`() {
        presenter.start(viewMock)

        then(viewMock).should().onStateUpdated(initialState)
    }

    @Test
    fun `given presenter is stopped then text changes then no view update should happen`() {
        presenter.start(viewMock)
        presenter.stop()
        reset(viewMock)

        presenter.textChanged(editable)

        then(viewMock).shouldHaveNoMoreInteractions()
    }

    @Test
    fun `given card input result called then view should have state updated`() {
        val expectedState = CardInputPresenter.CardInputUiState(
            initialState.cardNumber,
            initialState.cardType,
            initialState.inputFinished,
            initialState.showCardError
        )
        given(editable.toString()).willReturn(initialState.cardNumber)
        presenter.start(viewMock)

        presenter.textChanged(editable)

        then(viewMock).should().onStateUpdated(expectedState)
    }

    @Test
    fun `given card input looses focus with no card number entered then should show error`() {
        val cardInputUiState =
            CardInputPresenter.CardInputUiState(cardNumber = "", showCardError = true)
        initPresenterWithUiState(cardInputUiState)
        presenter.start(viewMock)
        reset(viewMock)

        presenter.focusChanged(false)

        then(viewMock).should().onStateUpdated(cardInputUiState)
    }

    private fun initPresenterWithUiState(cardInputUiState: CardInputPresenter.CardInputUiState) {
        initialState = cardInputUiState
        presenter = CardInputPresenter(dataStore, initialState)
    }
}
