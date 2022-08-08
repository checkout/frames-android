package com.checkout.frames.style.component

import androidx.annotation.ColorLong
import androidx.annotation.DrawableRes
import com.checkout.frames.model.Padding

public data class ImageStyle(
    /** Image resource. */
    @DrawableRes
    val image: Int,
    /** The 32-bit ARGB container background color. */
    @ColorLong
    val tinColor: Long? = null,
    /** Height in dp. */
    val height: Int? = null,
    /** Width in dp. */
    val width: Int? = null,
    val padding: Padding? = null
)
