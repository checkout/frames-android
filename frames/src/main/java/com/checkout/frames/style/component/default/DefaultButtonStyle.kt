package com.checkout.frames.style.component.default

import androidx.annotation.ColorLong
import androidx.annotation.StringRes
import com.checkout.frames.model.Shape
import com.checkout.frames.model.CornerRadius
import com.checkout.frames.model.BorderStroke
import com.checkout.frames.model.Padding
import com.checkout.frames.model.Margin
import com.checkout.frames.model.font.FontWeight
import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.ImageStyle
import com.checkout.frames.utils.constants.BorderConstants
import com.checkout.frames.utils.constants.ButtonStyleConstants
import com.checkout.frames.utils.constants.LightStyleConstants

@SuppressWarnings("LongParameterList")
public object DefaultButtonStyle {

    public fun lightOutline(
        text: String = "",
        @StringRes
        textId: Int? = null,
        @ColorLong
        contentColor: Long = LightStyleConstants.focusedBorderColor,
        @ColorLong
        containerColor: Long = ButtonStyleConstants.colorTransparent,
        @ColorLong
        disabledContentColor: Long = LightStyleConstants.focusedBorderColor,
        @ColorLong
        disabledContainerColor: Long = ButtonStyleConstants.colorTransparent,
        shape: Shape = Shape.RoundCorner,
        cornerRadius: CornerRadius = CornerRadius(BorderConstants.radius),
        borderStroke: BorderStroke? = BorderStroke(
            BorderConstants.unfocusedBorderThickness,
            ButtonStyleConstants.outlineStrokeColor
        ),
        contentPadding: Padding = ButtonStyleConstants.outlineContentPadding,
        leadingIconStyle: ImageStyle? = null,
        trailingIconStyle: ImageStyle? = null,
        margin: Margin? = null,
        fontWeight: FontWeight = FontWeight.Normal
    ): ButtonStyle = ButtonStyle(
        contentColor = contentColor,
        containerColor = containerColor,
        disabledContentColor = disabledContentColor,
        disabledContainerColor = disabledContainerColor,
        shape = shape,
        cornerRadius = cornerRadius,
        borderStroke = borderStroke,
        contentPadding = contentPadding,
        textStyle = DefaultTextLabelStyle.title(
            text = text,
            textId = textId,
            leadingIconStyle = leadingIconStyle,
            trailingIconStyle = trailingIconStyle,
            fontWeight = fontWeight
        ),
        containerStyle = ContainerStyle(margin = margin)
    )

    public fun lightSolid(
        text: String = "",
        @StringRes
        textId: Int? = null,
        @ColorLong
        contentColor: Long = LightStyleConstants.focusedBorderColor,
        @ColorLong
        containerColor: Long = ButtonStyleConstants.colorTransparent,
        @ColorLong
        disabledContentColor: Long = LightStyleConstants.focusedBorderColor,
        @ColorLong
        disabledContainerColor: Long = ButtonStyleConstants.colorTransparent,
        shape: Shape = Shape.RoundCorner,
        cornerRadius: CornerRadius = CornerRadius(BorderConstants.radius),
        contentPadding: Padding = ButtonStyleConstants.solidContentPadding,
        leadingIconStyle: ImageStyle? = null,
        trailingIconStyle: ImageStyle? = null,
        margin: Margin? = null,
        fontWeight: FontWeight = FontWeight.Normal
    ): ButtonStyle = lightOutline(
        text = text,
        textId = textId,
        contentColor = contentColor,
        containerColor = containerColor,
        disabledContentColor = disabledContentColor,
        disabledContainerColor = disabledContainerColor,
        shape = shape,
        cornerRadius = cornerRadius,
        borderStroke = null,
        contentPadding = contentPadding,
        leadingIconStyle = leadingIconStyle,
        trailingIconStyle = trailingIconStyle,
        margin = margin,
        fontWeight = fontWeight
    )
}
