package com.checkout.sdk.cardinput

import android.text.Editable
import com.checkout.sdk.architecture.BasePresenter
import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.architecture.UiState
import com.checkout.sdk.store.DataStore
import com.checkout.sdk.utils.CardUtils


class CardInputPresenter(
    private val dataStore: DataStore,
    initialState: CardInputUiState = CardInputUiState()
) :
    BasePresenter<CardInputPresenter.CardInputView, CardInputPresenter.CardInputUiState>(
        initialState
    ) {

    fun textChanged(text: Editable) {
        val cardInputResult = CardInputUseCase(text, dataStore).execute()
        val newState = uiState.copy(
            cardNumber = cardInputResult.cardNumber,
            cardType = cardInputResult.cardType,
            inputFinished = cardInputResult.inputFinished,
            showCardError = false
        )
        safeUpdateView(newState)
    }

    fun focusChanged(hasFocus: Boolean) {
        val cardError = CardFocusUseCase(hasFocus, dataStore.cardNumber).execute()
        val newState = uiState.copy(
            showCardError = cardError
        )
        safeUpdateView(newState)
    }

    data class CardInputUiState(
        val cardNumber: String = "",
        val cardType: CardUtils.Cards = CardUtils.Cards.DEFAULT,
        val inputFinished: Boolean = false,
        val showCardError: Boolean = false
    ) : UiState

    interface CardInputView : MvpView<CardInputUiState>

}
