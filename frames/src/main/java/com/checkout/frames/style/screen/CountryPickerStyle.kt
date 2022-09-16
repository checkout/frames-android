package com.checkout.frames.style.screen

import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.style.component.base.TextLabelStyle

public data class CountryPickerStyle(
    var screenTitleStyle: TextLabelStyle = TextLabelStyle(),
    var searchFieldStyle: InputFieldStyle = InputFieldStyle(),
    var searchItemStyle: TextLabelStyle = TextLabelStyle(),
    var containerStyle: ContainerStyle = ContainerStyle(),
    var withFlag: Boolean = true
)
