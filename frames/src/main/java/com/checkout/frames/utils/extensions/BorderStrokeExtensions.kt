package com.checkout.frames.utils.extensions

import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

internal fun com.checkout.frames.model.BorderStroke.toComposeStroke(): BorderStroke =
    BorderStroke(this.width.dp, Color(this.color))
