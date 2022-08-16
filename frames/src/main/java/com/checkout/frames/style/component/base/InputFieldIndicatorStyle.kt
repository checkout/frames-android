package com.checkout.frames.style.component.base

import androidx.annotation.ColorLong
import com.checkout.frames.model.CornerRadius
import com.checkout.frames.model.Shape
import com.checkout.frames.utils.constants.BorderConstants
import com.checkout.frames.utils.constants.UnderlineConstants

public sealed class InputFieldIndicatorStyle {

    public data class Border(
        val shape: Shape = Shape.RoundCorner,
        /** Radius in dp. */
        val cornerRadius: CornerRadius = CornerRadius(BorderConstants.radius),
        /** Focused border thickness in dp. */
        val focusedBorderThickness: Int = BorderConstants.focusedBorderThickness,
        /** Unfocused border thickness in dp. */
        val unfocusedBorderThickness: Int = BorderConstants.unfocusedBorderThickness,
        /** The 32-bit ARGB color to create a focused border Color from. */
        @ColorLong
        val focusedBorderColor: Long = BorderConstants.focusedBorderColor,
        /** The 32-bit ARGB color to create an unfocused border Color from. */
        @ColorLong
        val unfocusedBorderColor: Long = BorderConstants.unfocusedBorderColor,
        /** The 32-bit ARGB color to create a disabled border Color from. */
        @ColorLong
        val disabledBorderColor: Long = BorderConstants.disabledBorderColor,
        /** The 32-bit ARGB color to create an error border Color from. */
        @ColorLong
        val errorBorderColor: Long = BorderConstants.errorBorderColor
    ) : InputFieldIndicatorStyle()

    public data class Underline(
        /** Focused underline thickness in dp. */
        val focusedUnderlineThickness: Int = UnderlineConstants.focusedUnderlineThickness,
        /** Unfocused underline thickness in dp. */
        val unfocusedUnderlineThickness: Int = UnderlineConstants.unfocusedUnderlineThickness,
        /** The 32-bit ARGB color to create a focused border Color from. */
        @ColorLong
        val focusedUnderlineColor: Long = UnderlineConstants.focusedUnderlineColor,
        /** The 32-bit ARGB color to create an unfocused border Color from. */
        @ColorLong
        val unfocusedUnderlineColor: Long = UnderlineConstants.unfocusedUnderlineColor,
        /** The 32-bit ARGB color to create a disabled border Color from. */
        @ColorLong
        val disabledUnderlineColor: Long = UnderlineConstants.disabledUnderlineColor,
        /** The 32-bit ARGB color to create an error border Color from. */
        @ColorLong
        val errorUnderlineColor: Long = UnderlineConstants.errorUnderlineColor
    ) : InputFieldIndicatorStyle()
}
