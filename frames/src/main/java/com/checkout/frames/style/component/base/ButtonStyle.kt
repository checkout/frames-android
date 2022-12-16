package com.checkout.frames.style.component.base

import androidx.annotation.ColorLong
import com.checkout.frames.model.Shape
import com.checkout.frames.model.CornerRadius
import com.checkout.frames.model.BorderStroke
import com.checkout.frames.model.ButtonElevation
import com.checkout.frames.model.Padding

public data class ButtonStyle(
    /** The 32-bit ARGB container color of this Button when enabled. */
    @ColorLong
    val containerColor: Long = 0x00000000,
    /** The 32-bit ARGB container color of this Button when not enabled. */
    @ColorLong
    val disabledContainerColor: Long = 0x00000000,
    /** The 32-bit ARGB content color of this Button when enabled. */
    @ColorLong
    val contentColor: Long = 0xFF000000,
    /** The 32-bit ARGB content color of this Button when not enabled. */
    @ColorLong
    val disabledContentColor: Long = 0xFF000000,
    /** Button shape. */
    val shape: Shape = Shape.Rectangle,
    /** Corner radius of the button shape or border stroke. */
    val cornerRadius: CornerRadius = CornerRadius(),
    /** Border stroke details. */
    val borderStroke: BorderStroke? = null,
    /** Button elevation in dp. */
    val buttonElevation: ButtonElevation = ButtonElevation(),
    /** Content padding in dp. */
    val contentPadding: Padding = Padding(),
    /** The text label style. Can be used to set leading and trailing icons. */
    val textStyle: TextLabelStyle = TextLabelStyle(),
    /** Top level button container style. */
    val containerStyle: ContainerStyle = ContainerStyle()
)
