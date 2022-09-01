package com.checkout.frames.style.screen

import com.checkout.frames.style.component.CardNumberComponentStyle
import com.checkout.frames.style.component.CvvComponentStyle
import com.checkout.frames.style.component.default.DefaultCardNumberComponentStyle
import com.checkout.frames.style.component.default.DefaultCvvComponentStyle

public data class PaymentDetailsStyle(
    public var cardNumberStyle: CardNumberComponentStyle = DefaultCardNumberComponentStyle.light(),
    public var cvvComponentStyle: CvvComponentStyle = DefaultCvvComponentStyle.light()
)
