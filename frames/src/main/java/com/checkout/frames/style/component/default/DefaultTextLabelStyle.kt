package com.checkout.frames.style.component.default

import androidx.annotation.ColorLong
import androidx.annotation.DrawableRes
import com.checkout.frames.R
import com.checkout.frames.model.Padding
import com.checkout.frames.model.font.Font
import com.checkout.frames.style.component.base.ImageStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.component.base.TextStyle
import com.checkout.frames.utils.constants.ErrorConstants

public object DefaultTextLabelStyle {
    public fun error(
        fontSize: Int = ErrorConstants.fontSize,
        font: Font = ErrorConstants.font,
        @ColorLong
        color: Long = ErrorConstants.color,
        leadingIconSize: Int = ErrorConstants.leadingIconSize,
        @DrawableRes
        leadingIconId: Int = R.drawable.cko_ic_error
    ): TextLabelStyle = TextLabelStyle(
        text = "",
        textStyle = TextStyle(
            size = fontSize,
            color = color,
            font = font
        ),
        leadingIconStyle = ImageStyle(
            image = leadingIconId,
            tinColor = color,
            height = leadingIconSize,
            width = leadingIconSize,
            padding = Padding(end = ErrorConstants.leadingIconEndPadding)
        )
    )
}
