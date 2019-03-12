package com.checkout.sdk.cardinput

import com.checkout.sdk.architecture.UiState
import com.checkout.sdk.utils.CardUtils

data class CardInputUiState(
    val cardNumber: String = "",
    val cardType: CardUtils.Cards = CardUtils.Cards.DEFAULT,
    val showCardError: Boolean = false
) : UiState
