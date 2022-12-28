package com.checkout.frames.style.screen

import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.style.component.base.TextLabelStyle

public data class CountryPickerStyle @JvmOverloads constructor(
    var screenTitleStyle: TextLabelStyle = TextLabelStyle(),
    var searchFieldStyle: InputFieldStyle = InputFieldStyle(),
    var searchItemStyle: TextLabelStyle = TextLabelStyle(),
    var containerStyle: ContainerStyle = ContainerStyle(color = 0xFFFFFFFF),
    var withFlag: Boolean = true
)
