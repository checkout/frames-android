package com.checkout.frames.mapper

import com.checkout.base.mapper.Mapper
import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.view.InternalButtonState
import com.checkout.frames.view.TextLabelState

internal class ButtonStyleToInternalStateMapper(
    private val textLabelMapper: Mapper<TextLabelStyle?, TextLabelState>
) : Mapper<ButtonStyle, InternalButtonState> {

    override fun map(from: ButtonStyle): InternalButtonState = InternalButtonState(
        textState = textLabelMapper.map(from.textStyle)
    )
}
