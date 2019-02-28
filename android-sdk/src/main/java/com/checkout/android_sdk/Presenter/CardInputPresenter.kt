package com.checkout.android_sdk.Presenter

import android.text.Editable
import com.checkout.android_sdk.Store.DataStore
import com.checkout.android_sdk.UseCase.CardFocusUseCase
import com.checkout.android_sdk.UseCase.CardInputUseCase
import com.checkout.android_sdk.Utils.CardUtils


class CardInputPresenter @JvmOverloads constructor(
    private val view: CardInputView,
    private val dataStore: DataStore,
    private var cardInputUiState: CardInputUiState = CardInputUiState()
) : CardInputUseCase.Callback,
    CardFocusUseCase.Callback {

    fun textChanged(text: Editable) {
        CardInputUseCase(text, dataStore, this).execute()
    }

    fun focusChanged(hasFocus: Boolean, cardNumber: String) {
        CardFocusUseCase(hasFocus, cardNumber, this).execute()
    }

    override fun onCardInputResult(cardInputResult: CardInputUseCase.CardInputResult) {
        cardInputUiState = cardInputUiState.copy(
            cardNumber = cardInputResult.cardNumber,
            cardType = cardInputResult.cardType,
            inputFinished = cardInputResult.inputFinished,
            showCardError = false
        )
        view.onCardInputStateUpdated(cardInputUiState)
    }

    override fun onCardFocusResult(cardError: Boolean) {
        cardInputUiState = cardInputUiState.copy(
            showCardError = cardError
        )
        view.onCardInputStateUpdated(cardInputUiState)
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
