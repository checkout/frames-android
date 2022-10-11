package com.checkout.frames.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

internal data class InternalButtonState(
    val isEnabled: MutableState<Boolean> = mutableStateOf(true),
    val textState: TextLabelState
)
