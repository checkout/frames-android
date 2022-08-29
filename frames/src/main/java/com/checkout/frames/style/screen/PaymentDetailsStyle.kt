package com.checkout.frames.style.screen

import com.checkout.frames.style.component.CardNumberComponentStyle
import com.checkout.frames.style.component.default.DefaultCardNumberComponentStyle

public data class PaymentDetailsStyle(
    public var cardNumberStyle: CardNumberComponentStyle = DefaultCardNumberComponentStyle.light()
)
