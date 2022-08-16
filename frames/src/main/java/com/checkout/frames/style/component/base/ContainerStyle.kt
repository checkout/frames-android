package com.checkout.frames.style.component.base

import androidx.annotation.ColorLong
import com.checkout.frames.model.CornerRadius
import com.checkout.frames.model.Margin
import com.checkout.frames.model.Padding
import com.checkout.frames.model.Shape
import com.checkout.frames.utils.ContainerConstants

public data class ContainerStyle(
    /**
     * The 32-bit ARGB container background color.
     * Transparent by default.
     */
    @ColorLong
    val color: Long = ContainerConstants.backgroundColor,
    /** Shape of the background. */
    val shape: Shape = ContainerConstants.shape,
    /**
     * Corner radius in dp. Applicable for RoundCorner and CutCorner shapes.
     */
    val cornerRadius: CornerRadius = CornerRadius(ContainerConstants.radius),
    /** Width in dp. */
    val width: Int? = null,
    /** Height in dp. */
    val height: Int? = null,
    val padding: Padding? = null,
    val margin: Margin? = null
)
