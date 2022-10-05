package com.checkout.frames.mapper

import com.checkout.base.mapper.Mapper
import com.checkout.frames.component.cardscheme.CardSchemeComponentState
import com.checkout.frames.style.component.CardSchemeComponentStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.view.TextLabelState

internal class CardSchemeComponentStyleToStateMapper(
    private val textLabelMapper: Mapper<TextLabelStyle?, TextLabelState>
) : Mapper<CardSchemeComponentStyle, CardSchemeComponentState> {

    override fun map(from: CardSchemeComponentStyle): CardSchemeComponentState {
        return CardSchemeComponentState(
            textLabelState = textLabelMapper.map(from.titleStyle),
        )
    }
}
