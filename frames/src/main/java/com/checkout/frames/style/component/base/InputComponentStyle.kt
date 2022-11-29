package com.checkout.frames.style.component.base

import com.checkout.frames.style.component.default.DefaultTextLabelStyle

public data class InputComponentStyle(
    val titleStyle: TextLabelStyle? = null,
    val subtitleStyle: TextLabelStyle? = null,
    val infoStyle: TextLabelStyle? = null,
    val inputFieldStyle: InputFieldStyle = InputFieldStyle(),
    val errorMessageStyle: TextLabelStyle? = DefaultTextLabelStyle.error(),
    val containerStyle: ContainerStyle = ContainerStyle(),
    val isInputFieldOptional: Boolean = false
)
