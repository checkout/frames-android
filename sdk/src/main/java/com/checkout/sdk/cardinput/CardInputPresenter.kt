package com.checkout.sdk.cardinput

import android.text.Editable
import com.checkout.sdk.architecture.BasePresenter
import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.store.DataStore


class CardInputPresenter(
    private val dataStore: DataStore,
    initialState: CardInputUiState = CardInputUiState()
) : BasePresenter<MvpView<CardInputUiState>, CardInputUiState>(initialState) {

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
}
