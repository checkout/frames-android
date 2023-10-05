package com.checkout.frames.cvvinputfield.style

import com.checkout.frames.style.component.base.InputFieldIndicatorStyle
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.style.component.default.DefaultLightStyle

public object DefaultCVVInputFieldStyle {
    public fun create(): InputFieldStyle = InputFieldStyle(
        textStyle = DefaultLightStyle.inputFieldTextStyle(),
        indicatorStyle = InputFieldIndicatorStyle.Underline(),
        placeholderStyle = DefaultLightStyle.placeHolderTextStyle(),
    )
}
