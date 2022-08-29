package com.checkout.frames.style.component

import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.utils.constants.CardNumberComponentConstants

public data class CardNumberComponentStyle(
    var inputStyle: InputComponentStyle = InputComponentStyle(),
    var cardNumberSeparator: Char = CardNumberComponentConstants.cardNumberSeparator
)
