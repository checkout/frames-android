package com.checkout.sdk.cardinput

import com.checkout.sdk.architecture.UiState
import com.checkout.sdk.core.Card

data class CardInputUiState(
    val cardNumber: String = "",
    val cardType: Card = Card.DEFAULT,
    val showCardError: Boolean = false
) : UiState
