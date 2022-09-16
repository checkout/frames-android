package com.checkout.frames.mapper

import com.checkout.base.mapper.Mapper
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.view.InputFieldState
import com.checkout.frames.view.TextLabelState

internal class InputComponentStyleToStateMapper(
    private val textLabelMapper: Mapper<TextLabelStyle?, TextLabelState>,
    private val inputFieldStateMapper: Mapper<InputFieldStyle, InputFieldState>
) : Mapper<InputComponentStyle, InputComponentState> {

    override fun map(from: InputComponentStyle): InputComponentState {
        return InputComponentState(
            inputFieldState = inputFieldStateMapper.map(from.inputFieldStyle),
            titleState = textLabelMapper.map(from.titleStyle),
            subtitleState = textLabelMapper.map(from.subtitleStyle),
            infoState = textLabelMapper.map(from.infoStyle),
            errorState = textLabelMapper.map(from.errorMessageStyle)
        )
    }
}
