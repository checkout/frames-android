package com.checkout.android_sdk.Presenter

import android.text.Editable
import com.checkout.android_sdk.Store.DataStore
import com.checkout.android_sdk.UseCase.CardFocusUseCase
import com.checkout.android_sdk.UseCase.CardInputUseCase
import com.checkout.android_sdk.Utils.CardUtils


class CardInputPresenter(private val dataStore: DataStore) : Presenter, CardInputUseCase.Callback,
    CardFocusUseCase.Callback {

    private var cardInputUiState: CardInputUiState = CardInputUiState()

    private var view: CardInputView? = null

    fun start(view: CardInputView) {
        this.view = view
        safeUpdateView()
    }

    fun stop() {
        view = null
    }

    fun textChanged(text: Editable) {
        CardInputUseCase(text, dataStore, this).execute()
    }

    fun focusChanged(hasFocus: Boolean) {
        CardFocusUseCase(hasFocus, dataStore.cardNumber, this).execute()
    }

    override fun onCardInputResult(cardInputResult: CardInputUseCase.CardInputResult) {
        cardInputUiState = cardInputUiState.copy(
            cardNumber = cardInputResult.cardNumber,
            cardType = cardInputResult.cardType,
            inputFinished = cardInputResult.inputFinished,
            showCardError = false
        )
        safeUpdateView()
    }

    override fun onCardFocusResult(cardError: Boolean) {
        cardInputUiState = cardInputUiState.copy(
            showCardError = cardError
        )
        safeUpdateView()
    }

    private fun safeUpdateView() {
        view?.onCardInputStateUpdated(cardInputUiState)
    }

    data class CardInputUiState(
        val cardNumber: String = "",
        val cardType: CardUtils.Cards = CardUtils.Cards.DEFAULT,
        val inputFinished: Boolean = false,
        val showCardError: Boolean = false
    )

    interface CardInputView {
        fun onCardInputStateUpdated(cardInputResult: CardInputUiState)
    }

}
