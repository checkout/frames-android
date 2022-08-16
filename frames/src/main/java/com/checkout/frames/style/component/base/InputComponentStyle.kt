package com.checkout.frames.style.component.base

public data class InputComponentStyle(
    val titleStyle: TextLabelStyle? = null,
    val subtitleStyle: TextLabelStyle? = null,
    val infoStyle: TextLabelStyle? = null,
    val inputFieldStyle: InputFieldStyle,
    val errorMessageStyle: TextLabelStyle? = null,
    val containerStyle: ContainerStyle = ContainerStyle(),
)
