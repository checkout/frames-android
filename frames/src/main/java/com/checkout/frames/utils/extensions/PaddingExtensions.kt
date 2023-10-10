package com.checkout.frames.utils.extensions

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import com.checkout.frames.model.Padding

internal fun Padding.toPaddingValues() = PaddingValues(
    start = this.start.dp,
    top = this.top.dp,
    end = this.end.dp,
    bottom = this.bottom.dp,
)
