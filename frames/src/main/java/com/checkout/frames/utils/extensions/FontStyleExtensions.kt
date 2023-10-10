package com.checkout.frames.utils.extensions

import androidx.compose.ui.text.font.FontStyle
import com.checkout.frames.model.font.FontStyle.Italic
import com.checkout.frames.model.font.FontStyle.Normal

internal fun com.checkout.frames.model.font.FontStyle.toComposeFontStyle(): FontStyle = when (this) {
    Normal -> FontStyle.Normal
    Italic -> FontStyle.Italic
}
