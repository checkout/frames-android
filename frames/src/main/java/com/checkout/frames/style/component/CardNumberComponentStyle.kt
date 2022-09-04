package com.checkout.frames.style.component

import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.utils.constants.CARD_NUMBER_SEPARATOR

public data class CardNumberComponentStyle(
    var inputStyle: InputComponentStyle = InputComponentStyle(),
    var cardNumberSeparator: Char = CARD_NUMBER_SEPARATOR
)
