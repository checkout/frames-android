package com.checkout.frames.style.component.base

import androidx.annotation.StringRes

public data class TextLabelStyle(
    var text: String = "",
    @StringRes
    var textId: Int? = null,
    var textStyle: TextStyle = TextStyle(),
    var leadingIconStyle: ImageStyle? = null,
    var trailingIconStyle: ImageStyle? = null,
    var containerStyle: ContainerStyle = ContainerStyle()
)
