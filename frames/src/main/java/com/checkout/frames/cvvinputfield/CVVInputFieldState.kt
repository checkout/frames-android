package com.checkout.frames.cvvinputfield

import com.checkout.frames.view.InputFieldState

internal data class CVVInputFieldState(
    val inputFieldState: InputFieldState,
) {
    val cvv = inputFieldState.text
    val cvvLength = inputFieldState.maxLength
}
