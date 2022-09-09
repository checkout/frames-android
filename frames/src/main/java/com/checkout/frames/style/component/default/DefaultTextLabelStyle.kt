package com.checkout.frames.style.component.default

import androidx.annotation.ColorLong
import androidx.annotation.DrawableRes
import com.checkout.frames.R
import com.checkout.frames.model.Padding
import com.checkout.frames.model.font.Font
import com.checkout.frames.model.font.FontWeight
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.ImageStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.component.base.TextStyle
import com.checkout.frames.utils.constants.ErrorConstants
import com.checkout.frames.utils.constants.HeaderTitleConstants
import com.checkout.frames.utils.constants.SubtitleConstants
import com.checkout.frames.utils.constants.TitleConstants

public object DefaultTextLabelStyle {

    public fun error(
        fontSize: Int = ErrorConstants.fontSize,
        font: Font = ErrorConstants.font,
        @ColorLong
        color: Long = ErrorConstants.color,
        leadingIconSize: Int = ErrorConstants.leadingIconSize,
        @DrawableRes
        leadingIconId: Int = R.drawable.cko_ic_error,
        padding: Padding = Padding(top = ErrorConstants.errorMessageTopPadding)
    ): TextLabelStyle = TextLabelStyle(
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
        ),
        containerStyle = ContainerStyle(padding = padding)
    )

    public fun title(
        fontSize: Int = TitleConstants.fontSize,
        font: Font = TitleConstants.font,
        @ColorLong
        color: Long = TitleConstants.color,
        padding: Padding = Padding(),
        maxLines: Int = TitleConstants.maxLines
    ): TextLabelStyle = TextLabelStyle(
        textStyle = TextStyle(size = fontSize, color = color, font = font, maxLines = maxLines),
        containerStyle = ContainerStyle(padding = padding)
    )

    public fun subtitle(
        fontSize: Int = SubtitleConstants.fontSize,
        font: Font = SubtitleConstants.font,
        @ColorLong
        color: Long = SubtitleConstants.color,
        padding: Padding = Padding(),
        maxLines: Int = SubtitleConstants.maxLines
    ): TextLabelStyle = TextLabelStyle(
        textStyle = TextStyle(size = fontSize, color = color, font = font, maxLines = maxLines),
        containerStyle = ContainerStyle(padding = padding)
    )

    public fun headerTitle(
        fontSize: Int = HeaderTitleConstants.fontSize,
        fontWeight: FontWeight = FontWeight.Normal,
        font: Font = HeaderTitleConstants.font,
        @ColorLong
        color: Long = HeaderTitleConstants.color,
        padding: Padding = Padding(),
        maxLines: Int = HeaderTitleConstants.maxLines,
        leadingIconSize: Int = HeaderTitleConstants.leadingIconSize,
        leadingIconPadding: Padding = Padding()
    ): TextLabelStyle = TextLabelStyle(
        textStyle = TextStyle(
            size = fontSize,
            color = color,
            font = font,
            fontWeight = fontWeight,
            maxLines = maxLines
        ),
        leadingIconStyle = ImageStyle(
            tinColor = color,
            height = leadingIconSize,
            width = leadingIconSize,
            padding = leadingIconPadding
        ),
        containerStyle = ContainerStyle(padding = padding)
    )
}
