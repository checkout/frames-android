package com.checkout.frames.mapper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.checkout.base.mapper.Mapper
import com.checkout.frames.style.component.base.ImageStyle
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.view.InputFieldState

internal class InputFieldStyleToInputFieldStateMapper(
    private val imageMapper: Mapper<ImageStyle?, @Composable (() -> Unit)?>
) : Mapper<InputFieldStyle, InputFieldState> {

    override fun map(from: InputFieldStyle): InputFieldState = InputFieldState(
        maxLength = mutableStateOf(from.textStyle.maxLength),
        leadingIcon = mutableStateOf(imageMapper.map(from.leadingIconStyle)),
        trailingIcon = mutableStateOf(imageMapper.map(from.leadingIconStyle))
    )
}
