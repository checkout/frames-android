package com.checkout.frames.component.expirydate

import com.checkout.frames.component.base.InputComponentState

internal data class ExpiryDateComponentState(
    val inputState: InputComponentState
) {
    val expiryDate = inputState.inputFieldState.text
}
