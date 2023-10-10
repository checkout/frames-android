package com.checkout.frames.utils.extensions

import androidx.compose.ui.text.style.TextAlign
import com.checkout.frames.model.TextAlign.Center
import com.checkout.frames.model.TextAlign.End
import com.checkout.frames.model.TextAlign.Start

internal fun com.checkout.frames.model.TextAlign.toComposeTextAlign(): TextAlign = when (this) {
    Start -> TextAlign.Start
    Center -> TextAlign.Center
    End -> TextAlign.End
}
