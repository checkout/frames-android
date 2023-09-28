package com.checkout.frames.cvvinputfield

import com.checkout.frames.view.InputFieldState

internal class CVVInputFieldState(
    val inputFieldState: InputFieldState,
) {
    val cvv = inputFieldState.text
    val cvvLength = inputFieldState.maxLength
}
