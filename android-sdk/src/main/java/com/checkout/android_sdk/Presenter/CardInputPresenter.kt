package com.checkout.android_sdk.Presenter

import android.text.Editable
import com.checkout.android_sdk.Architecture.BasePresenter
import com.checkout.android_sdk.Architecture.MvpView
import com.checkout.android_sdk.Architecture.UiState
import com.checkout.android_sdk.Store.DataStore
import com.checkout.android_sdk.UseCase.CardFocusUseCase
import com.checkout.android_sdk.UseCase.CardInputUseCase
import com.checkout.android_sdk.Utils.CardUtils


class CardInputPresenter(private val dataStore: DataStore) :
    BasePresenter<CardInputPresenter.CardInputView, CardInputPresenter.CardInputUiState>(CardInputUiState()),
    CardInputUseCase.Callback,
    CardFocusUseCase.Callback {

    fun textChanged(text: Editable) {
        CardInputUseCase(text, dataStore, this).execute()
    }

    fun focusChanged(hasFocus: Boolean) {
        CardFocusUseCase(hasFocus, dataStore.cardNumber, this).execute()
    }

    override fun onCardInputResult(cardInputResult: CardInputUseCase.CardInputResult) {
        val newState = uiState.copy(
            cardNumber = cardInputResult.cardNumber,
            cardType = cardInputResult.cardType,
            inputFinished = cardInputResult.inputFinished,
            showCardError = false
        )
        safeUpdateView(newState)
    }

    override fun onCardFocusResult(cardError: Boolean) {
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
