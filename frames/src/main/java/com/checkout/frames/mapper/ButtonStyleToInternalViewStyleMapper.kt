package com.checkout.frames.mapper

import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.checkout.base.mapper.Mapper
import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.view.InternalButtonViewStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import com.checkout.frames.utils.extensions.toComposeShape
import com.checkout.frames.utils.extensions.toPaddingValues

internal class ButtonStyleToInternalViewStyleMapper(
    private val containerMapper: Mapper<ContainerStyle, Modifier>,
    private val textLabelMapper: Mapper<TextLabelStyle, TextLabelViewStyle>
) : Mapper<ButtonStyle, InternalButtonViewStyle> {

    override fun map(from: ButtonStyle): InternalButtonViewStyle = InternalButtonViewStyle(
        defaultElevation = from.buttonElevation.defaultElevation.dp,
        pressedElevation = from.buttonElevation.pressedElevation.dp,
        focusedElevation = from.buttonElevation.focusedElevation.dp,
        hoveredElevation = from.buttonElevation.hoveredElevation.dp,
        disabledElevation = from.buttonElevation.disabledElevation.dp,
        containerColor = Color(from.containerColor),
        disabledContainerColor = Color(from.disabledContainerColor),
        contentColor = Color(from.contentColor),
        disabledContentColor = Color(from.disabledContentColor),
        modifier = containerMapper.map(from.containerStyle),
        shape = from.shape.toComposeShape(from.cornerRadius),
        border = provideBorderStroke(from),
        contentPadding = from.contentPadding.toPaddingValues(),
        textStyle = textLabelMapper.map(from.textStyle)
    )

    private fun provideBorderStroke(style: ButtonStyle): BorderStroke? = style.borderStroke?.let {
        BorderStroke(it.width.dp, Color(it.color))
    }
}
