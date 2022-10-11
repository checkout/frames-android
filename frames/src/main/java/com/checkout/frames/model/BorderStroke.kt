package com.checkout.frames.model

import androidx.annotation.ColorLong

/**
 * Class to specify the stroke to draw border with.
 *
 * @param width width of the border in [Int].
 * @param color color to paint the border with
 */
public data class BorderStroke(
    /** Border width in dp. */
    val width: Int,
    /** The 32-bit ARGB border color. */
    @ColorLong
    val color: Long
)
