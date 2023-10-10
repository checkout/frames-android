package com.checkout.frames.component.base

import com.checkout.frames.view.InputFieldState
import com.checkout.frames.view.TextLabelState

internal data class InputComponentState(
    val inputFieldState: InputFieldState = InputFieldState(),
    val titleState: TextLabelState = TextLabelState(),
    val subtitleState: TextLabelState = TextLabelState(),
    val infoState: TextLabelState = TextLabelState(),
    val errorState: TextLabelState = TextLabelState(),
    val isInputFieldOptional: Boolean = false,
)
