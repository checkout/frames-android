package com.checkout.frames.utils.extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

internal fun com.checkout.frames.style.component.base.TextStyle.toComposeTextStyle(): TextStyle = TextStyle(
    fontSize = this.size.sp,
    color = Color(this.color),
    textAlign = this.textAlign.toComposeTextAlign(),
    fontFamily = this.font.toFontFamily(),
    fontStyle = this.fontStyle.toComposeFontStyle(),
    fontWeight = this.fontWeight.toComposeFontWeight(),
    lineHeight = this.lineHeight?.sp ?: TextUnit.Unspecified
)
