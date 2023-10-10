package com.checkout.frames.style.component

import androidx.annotation.StringRes
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.ImageStyle
import com.checkout.frames.style.component.base.TextStyle

public data class ScreenHeaderStyle @JvmOverloads constructor(
    var text: String = "",
    @StringRes
    var textId: Int? = null,
    var textStyle: TextStyle = TextStyle(),
    public var backIconStyle: ImageStyle = ImageStyle(),
    public var containerStyle: ContainerStyle = ContainerStyle(),
)
