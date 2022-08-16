package com.checkout.frames.style.component.base

public data class InputFieldStyle(
    val textStyle: TextStyle = TextStyle(),
    val placeholderText: String = "",
    val placeholderStyle: TextStyle = TextStyle(),
    val containerStyle: ContainerStyle = ContainerStyle(),
    val indicatorStyle: InputFieldIndicatorStyle = InputFieldIndicatorStyle.Border(),
    val leadingIconStyle: ImageStyle? = null,
    val trailingIconStyle: ImageStyle? = null,
)
