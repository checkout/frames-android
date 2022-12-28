package com.checkout.frames.style.component.base

import androidx.annotation.ColorLong
import com.checkout.frames.model.Margin

public data class DividerStyle @JvmOverloads constructor(
    /** Thickness in dp. */
    val thickness: Int = 1,
    /** The 32-bit ARGB color for divider. */
    @ColorLong
    val color: Long,
    val margin: Margin? = null
)
