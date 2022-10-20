package com.checkout.frames.mapper

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.checkout.base.mapper.Mapper
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import com.checkout.frames.utils.extensions.toComposeFontWeight
import com.checkout.frames.utils.extensions.toComposeFontStyle
import com.checkout.frames.utils.extensions.toComposeTextAlign
import com.checkout.frames.utils.extensions.toFontFamily

internal class TextLabelStyleToViewStyleMapper(
    private val containerMapper: Mapper<ContainerStyle, Modifier>
) : Mapper<TextLabelStyle, TextLabelViewStyle> {

    override fun map(from: TextLabelStyle): TextLabelViewStyle = TextLabelViewStyle(
        modifier = containerMapper.map(from.containerStyle),
        color = Color(from.textStyle.color),
        fontSize = from.textStyle.size.sp,
        textAlign = from.textStyle.textAlign.toComposeTextAlign(),
        fontFamily = from.textStyle.font.toFontFamily(),
        fontStyle = from.textStyle.fontStyle.toComposeFontStyle(),
        fontWeight = from.textStyle.fontWeight.toComposeFontWeight(),
        maxLines = from.textStyle.maxLines,
        lineHeight = from.textStyle.lineHeight?.sp ?: TextUnit.Unspecified
    )
}
