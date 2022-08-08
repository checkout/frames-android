package com.checkout.frames.style.component

public data class InputFieldStyle(
    val textStyle: TextStyle,
    val placeholderText: String,
    val placeholderStyle: TextStyle,
    val containerStyle: ContainerStyle = ContainerStyle(),
    val indicatorStyle: InputFieldIndicatorStyle = InputFieldIndicatorStyle.Border(),
    val leadingIconStyle: ImageStyle? = null,
    val trailingIconStyle: ImageStyle? = null,
)
