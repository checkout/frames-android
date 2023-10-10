package com.checkout.frames.utils.extensions

import androidx.compose.ui.text.font.FontWeight
import com.checkout.frames.model.font.FontWeight.Bold
import com.checkout.frames.model.font.FontWeight.ExtraBold
import com.checkout.frames.model.font.FontWeight.Light
import com.checkout.frames.model.font.FontWeight.Medium
import com.checkout.frames.model.font.FontWeight.Normal
import com.checkout.frames.model.font.FontWeight.SemiBold

internal fun com.checkout.frames.model.font.FontWeight.toComposeFontWeight(): FontWeight = when (this) {
    Light -> FontWeight.Light
    Normal -> FontWeight.Normal
    Medium -> FontWeight.Medium
    SemiBold -> FontWeight.SemiBold
    Bold -> FontWeight.Bold
    ExtraBold -> FontWeight.ExtraBold
}
