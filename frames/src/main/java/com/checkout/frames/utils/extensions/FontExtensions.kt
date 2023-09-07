package com.checkout.frames.utils.extensions

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.checkout.frames.model.font.Font.Cursive
import com.checkout.frames.model.font.Font.Custom
import com.checkout.frames.model.font.Font.Default
import com.checkout.frames.model.font.Font.Monospace
import com.checkout.frames.model.font.Font.SansSerif
import com.checkout.frames.model.font.Font.Serif

internal fun com.checkout.frames.model.font.Font.toFontFamily(): FontFamily = when (this) {
    is Default -> FontFamily.Default
    is Serif -> FontFamily.Serif
    is SansSerif -> FontFamily.SansSerif
    is Monospace -> FontFamily.Monospace
    is Cursive -> FontFamily.Cursive
    is Custom -> {
        val fonts = mutableListOf<Font>()

        fonts.add(Font(this.normalFont, weight = FontWeight.Normal))
        this.normalItalicFont?.let { fonts.add(Font(it, weight = FontWeight.Normal, style = FontStyle.Italic)) }
        this.lightFont?.let { fonts.add(Font(it, weight = FontWeight.Light)) }
        this.mediumFont?.let { fonts.add(Font(it, weight = FontWeight.Medium)) }
        this.semiBold?.let { fonts.add(Font(it, weight = FontWeight.SemiBold)) }
        this.boldFont?.let { fonts.add(Font(it, weight = FontWeight.Bold)) }
        this.extraBoldFont?.let { fonts.add(Font(it, weight = FontWeight.ExtraBold)) }

        FontFamily(fonts)
    }
}
