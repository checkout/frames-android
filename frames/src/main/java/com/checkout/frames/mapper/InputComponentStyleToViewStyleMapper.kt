package com.checkout.frames.mapper

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import com.checkout.base.mapper.Mapper
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import com.checkout.frames.style.view.InputFieldViewStyle
import com.checkout.frames.style.view.TextLabelViewStyle

internal class InputComponentStyleToViewStyleMapper(
    private val textLabelStyleMapper: Mapper<TextLabelStyle, TextLabelViewStyle>,
    private val inputFieldStyleMapper: Mapper<InputFieldStyle, InputFieldViewStyle>,
    private val containerMapper: Mapper<ContainerStyle, Modifier>
) : Mapper<InputComponentStyle, InputComponentViewStyle> {

    override fun map(from: InputComponentStyle) = InputComponentViewStyle(
        titleStyle = from.titleStyle?.let { textLabelStyleMapper.map(it) } ?: TextLabelViewStyle(),
        subtitleStyle = from.subtitleStyle?.let { textLabelStyleMapper.map(it) } ?: TextLabelViewStyle(),
        infoStyle = from.infoStyle?.let { textLabelStyleMapper.map(it) } ?: TextLabelViewStyle(),
        errorMessageStyle = from.errorMessageStyle?.let { textLabelStyleMapper.map(it) } ?: TextLabelViewStyle(),
        inputFieldStyle = inputFieldStyleMapper.map(from.inputFieldStyle),
        containerModifier = containerMapper.map(from.containerStyle).fillMaxWidth(),
        isInputFieldOptional = from.isInputFieldOptional
    )
}
