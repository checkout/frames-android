package com.checkout.frames.component.cardnumber

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.checkout.base.model.CardScheme
import com.checkout.frames.component.base.InputComponentState

internal data class CardNumberComponentState(
    val inputState: InputComponentState,
    val cardScheme: MutableState<CardScheme> = mutableStateOf(CardScheme.UNKNOWN)
) {
    val cardNumber = inputState.inputFieldState.text
}
