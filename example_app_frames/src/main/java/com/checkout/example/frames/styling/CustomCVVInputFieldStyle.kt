package com.checkout.example.frames.styling

import com.checkout.example.frames.paymentformstyling.PaymentFormConstants.backgroundColor
import com.checkout.example.frames.paymentformstyling.PaymentFormConstants.placeHolderTextColor
import com.checkout.example.frames.paymentformstyling.PaymentFormConstants.textColor
import com.checkout.frames.R
import com.checkout.frames.model.CornerRadius
import com.checkout.frames.model.Padding
import com.checkout.frames.model.Shape
import com.checkout.frames.model.font.Font
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.CursorStyle
import com.checkout.frames.style.component.base.InputFieldIndicatorStyle
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.style.component.base.TextStyle

@Suppress("MagicNumber")
object CustomCVVInputFieldStyle {

    fun create() = InputFieldStyle(
        placeholderTextId = R.string.enter_cvv_here,
        textStyle = TextStyle(16, color = textColor, font = Font.Cursive),
        placeholderStyle = TextStyle(16, color = placeHolderTextColor, font = Font.Cursive),
        containerStyle = ContainerStyle(
            width = 250,
            color = backgroundColor,
            padding = Padding(end = 2),
            shape = Shape.RoundCorner,
            cornerRadius = CornerRadius(9)
        ),
        cursorStyle = CursorStyle(0XFF00CC2D),
        indicatorStyle = InputFieldIndicatorStyle.Underline(
            focusedUnderlineThickness = 0, unfocusedUnderlineThickness = 0
        )
    )
}
