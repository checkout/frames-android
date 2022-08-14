package com.checkout.frames.mapper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.checkout.base.mapper.Mapper
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.style.component.base.ImageStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.view.InputFieldState
import com.checkout.frames.view.TextLabelState

internal class InputComponentStyleToStateMapper(
    private val imageMapper: Mapper<ImageStyle?, @Composable (() -> Unit)?>,
    private val textLabelMapper: Mapper<TextLabelStyle?, TextLabelState>
) : Mapper<InputComponentStyle, InputComponentState> {

    override fun map(from: InputComponentStyle): InputComponentState {
        return InputComponentState(
            inputFieldState = InputFieldState(
                leadingIcon = mutableStateOf(imageMapper.map(from.inputFieldStyle.leadingIconStyle)),
                trailingIcon = mutableStateOf(imageMapper.map(from.inputFieldStyle.leadingIconStyle))
            ),
            titleState = textLabelMapper.map(from.titleStyle),
            subtitleState = textLabelMapper.map(from.subtitleStyle),
            infoState = textLabelMapper.map(from.infoStyle),
            errorState = textLabelMapper.map(from.errorMessageStyle)
        )
    }
}
