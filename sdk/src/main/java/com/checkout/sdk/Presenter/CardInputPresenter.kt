package com.checkout.sdk.Presenter

import android.text.Editable
import com.checkout.sdk.Architecture.BasePresenter
import com.checkout.sdk.Architecture.MvpView
import com.checkout.sdk.Architecture.UiState
import com.checkout.sdk.Store.DataStore
import com.checkout.sdk.UseCase.CardFocusUseCase
import com.checkout.sdk.UseCase.CardInputUseCase
import com.checkout.sdk.Utils.CardUtils


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
