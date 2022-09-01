package com.checkout.frames.style.component.default

import com.checkout.frames.R
import com.checkout.frames.style.component.CardNumberComponentStyle

public object DefaultCardNumberComponentStyle {

    public fun light(): CardNumberComponentStyle {
        val style = DefaultLightStyle.inputComponentStyle(titleTextId = R.string.cko_card_number_title, withLeadingIcon = true)
        return CardNumberComponentStyle(style)
    }
}
