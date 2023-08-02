package com.checkout.frames.component.cardholdername

import com.checkout.frames.component.base.InputComponentState

internal data class CardHolderNameComponentState(
    val inputState: InputComponentState,
) {
    val cardHolderName by lazy { inputState.inputFieldState.text }
}
