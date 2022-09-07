package com.checkout.frames.style.component.base

import androidx.annotation.StringRes

public data class InputFieldStyle(
    val textStyle: TextStyle = TextStyle(),
    var placeholderText: String = "",
    @StringRes
    var placeholderTextId: Int? = null,
    val placeholderStyle: TextStyle = TextStyle(),
    val containerStyle: ContainerStyle = ContainerStyle(),
    val indicatorStyle: InputFieldIndicatorStyle = InputFieldIndicatorStyle.Border(),
    val leadingIconStyle: ImageStyle? = null,
    val trailingIconStyle: ImageStyle? = null,
    val cursorStyle: CursorStyle? = null
)
