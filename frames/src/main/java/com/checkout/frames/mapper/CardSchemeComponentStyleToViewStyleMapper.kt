package com.checkout.frames.mapper

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import com.checkout.base.mapper.Mapper
import com.checkout.frames.style.component.CardSchemeComponentStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.view.CardSchemeComponentViewStyle
import com.checkout.frames.style.view.TextLabelViewStyle

internal class CardSchemeComponentStyleToViewStyleMapper(
    private val textLabelStyleMapper: Mapper<TextLabelStyle, TextLabelViewStyle>,
    private val containerMapper: Mapper<ContainerStyle, Modifier>
) : Mapper<CardSchemeComponentStyle, CardSchemeComponentViewStyle> {

    override fun map(from: CardSchemeComponentStyle) = CardSchemeComponentViewStyle(
        titleStyle = from.titleStyle.let { textLabelStyleMapper.map(it) },
        containerModifier = containerMapper.map(from.imagesContainerStyle).fillMaxWidth()
    )
}
