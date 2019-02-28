package com.checkout.android_sdk.Presenter

import android.text.Editable
import com.checkout.android_sdk.Store.DataStore
import com.checkout.android_sdk.UseCase.CardInputUseCase
import com.checkout.android_sdk.Utils.CardUtils
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
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

    private lateinit var presenter: CardInputPresenter

    @Before
    fun onSetup() {
        presenter = CardInputPresenter(dataStore)
    }

    @Test
    fun `given presenter is started then default state should be set`() {
        presenter.start(viewMock)

        then(viewMock).should().onCardInputStateUpdated(CardInputPresenter.CardInputUiState())
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
    fun `given card input result called then viewMock should have state updated`() {
        val cardInputResult = CardInputUseCase.CardInputResult("1234", CardUtils.Cards.JCB, false, false)
        val cardInputUiState = CardInputPresenter.CardInputUiState(cardInputResult.cardNumber, cardInputResult.cardType, cardInputResult.inputFinished, cardInputResult.showError)
        presenter.start(viewMock)

        presenter.onCardInputResult(cardInputResult)

        then(viewMock).should().onCardInputStateUpdated(cardInputUiState)
    }

    @Test
    fun `given card focus result called then viewMock should have state updated`() {
        val cardInputUiState = CardInputPresenter.CardInputUiState(showCardError = true)
        presenter.start(viewMock)

        presenter.onCardFocusResult(true)

        then(viewMock).should().onCardInputStateUpdated(cardInputUiState)
    }

}
