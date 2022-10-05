package com.checkout.frames.style.component.default

import com.checkout.frames.R
import com.checkout.frames.style.component.CardSchemeComponentStyle

public object DefaultCardSchemeComponentStyle {

    public fun light(): CardSchemeComponentStyle {
        val style = DefaultLightStyle.cardSchemeComponentStyle(
            titleTextId = R.string.cko_accepted_cards_title
        )
        return CardSchemeComponentStyle(style.titleStyle, style.imageStyle, style.imagesContainerStyle)
    }
}
