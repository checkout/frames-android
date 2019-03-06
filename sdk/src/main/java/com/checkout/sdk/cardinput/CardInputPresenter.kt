package com.checkout.sdk.cardinput

import com.checkout.sdk.architecture.BasePresenter
import com.checkout.sdk.architecture.MvpView


class CardInputPresenter(initialState: CardInputUiState = CardInputUiState()) :
    BasePresenter<MvpView<CardInputUiState>, CardInputUiState>(initialState) {

    fun textChanged(cardInputUseCase: CardInputUseCase) {
        val cardInputResult = cardInputUseCase.execute()
        val newState = uiState.copy(
            cardNumber = cardInputResult.cardNumber,
            cardType = cardInputResult.cardType,
            inputFinished = cardInputResult.inputFinished,
            showCardError = cardInputResult.showError
        )
        safeUpdateView(newState)
    }

    fun focusChanged(cardFocusUseCase: CardFocusUseCase) {
        val cardError = cardFocusUseCase.execute()
        val newState = uiState.copy(
            showCardError = cardError
        )
        safeUpdateView(newState)
    }
}
