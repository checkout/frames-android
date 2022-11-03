package com.checkout.frames.style.component.default

import androidx.annotation.ColorLong
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import com.checkout.frames.R
import com.checkout.frames.model.Margin
import com.checkout.frames.model.Padding
import com.checkout.frames.model.font.Font
import com.checkout.frames.model.font.FontWeight
import com.checkout.frames.style.component.CardSchemeComponentStyle
import com.checkout.frames.style.component.ScreenHeaderStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.component.base.TextStyle
import com.checkout.frames.style.component.base.ImageStyle
import com.checkout.frames.style.component.base.ImageContainerStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.InputFieldIndicatorStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.utils.constants.LightStyleConstants
import com.checkout.frames.utils.constants.CardSchemeConstants
import com.checkout.frames.utils.constants.HeaderTitleConstants

@Suppress("TooManyFunctions", "LongParameterList")
public object DefaultLightStyle {

    public fun screenHeader(
        text: String = "",
        @StringRes
        textId: Int? = null,
        @DrawableRes
        imageId: Int? = null,
        fontSize: Int = HeaderTitleConstants.fontSize,
        fontWeight: FontWeight = FontWeight.Normal,
        font: Font = HeaderTitleConstants.font,
        @ColorLong
        textColor: Long = HeaderTitleConstants.textColor,
        padding: Padding = Padding(),
        height: Int = HeaderTitleConstants.height,
        maxLines: Int = HeaderTitleConstants.maxLines,
        leadingIconSize: Int = HeaderTitleConstants.leadingIconSize,
        leadingIconPadding: Padding = Padding()
    ): ScreenHeaderStyle = ScreenHeaderStyle(
        text = text,
        textId = textId,
        textStyle = TextStyle(
            size = fontSize,
            color = textColor,
            font = font,
            fontWeight = fontWeight,
            maxLines = maxLines
        ),
        backIconStyle = ImageStyle(
            image = imageId,
            tinColor = textColor,
            height = leadingIconSize,
            width = leadingIconSize,
            padding = leadingIconPadding
        ),
        containerStyle = ContainerStyle(height = height, padding = padding)
    )

    public fun screenTitleTextLabelStyle(
        padding: Padding = Padding()
    ): TextLabelStyle = DefaultTextLabelStyle.headerTitle(padding = padding)

    public fun titleTextLabelStyle(): TextLabelStyle = DefaultTextLabelStyle.title()

    public fun subtitleTextLabelStyle(): TextLabelStyle = DefaultTextLabelStyle.subtitle()

    public fun placeHolderTextStyle(): TextStyle = DefaultTextStyle.placeHolder()

    public fun leadingIconStyle(): ImageStyle = ImageStyle(
        padding = Padding(
            start = LightStyleConstants.leadingIconStartPadding,
            end = LightStyleConstants.leadingIconEndPadding
        ),
        height = LightStyleConstants.leadingIconHeight,
        width = LightStyleConstants.leadingIconWidth
    )

    public fun supportedCardSchemeIconStyle(): ImageStyle = ImageStyle(
        height = LightStyleConstants.leadingIconHeight,
        width = LightStyleConstants.leadingIconWidth
    )

    public fun supportedCardSchemeImageContainerStyle(): ImageContainerStyle = ImageContainerStyle(
        crossAxisSpacing = CardSchemeConstants.crossAxisSpacingPadding,
        mainAxisSpacing = CardSchemeConstants.mainAxisSpacingPadding,
        containerStyle = ContainerStyle(margin = Margin(top = CardSchemeConstants.marginTop))
    )

    public fun inputFieldTextStyle(): TextStyle = TextStyle(
        size = LightStyleConstants.fontSize,
        color = LightStyleConstants.fontColor,
        font = LightStyleConstants.font,
        maxLines = LightStyleConstants.maxLines
    )

    public fun indicatorStyle(): InputFieldIndicatorStyle = InputFieldIndicatorStyle.Border(
        unfocusedBorderColor = LightStyleConstants.unfocusedBorderColor,
        focusedBorderColor = LightStyleConstants.focusedBorderColor,
        errorBorderColor = LightStyleConstants.errorBorderColor
    )

    public fun errorTextLabelStyle(): TextLabelStyle = DefaultTextLabelStyle.error()

    public fun inputFieldStyle(
        withLeadingIcon: Boolean = false,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default
    ): InputFieldStyle =
        InputFieldStyle(
            textStyle = inputFieldTextStyle(),
            indicatorStyle = indicatorStyle(),
            placeholderStyle = placeHolderTextStyle(),
            leadingIconStyle = if (withLeadingIcon) leadingIconStyle() else null,
            containerStyle = ContainerStyle(margin = Margin(top = LightStyleConstants.marginTop)),
            keyboardOptions = keyboardOptions
        )

    public fun inputComponentStyle(
        titleText: String = "",
        @StringRes
        titleTextId: Int? = null,
        subtitleText: String = "",
        @StringRes
        subtitleTextId: Int? = null,
        infoText: String = "",
        @StringRes
        infoTextId: Int? = null,
        placeholderResourceText: String = "",
        @StringRes
        placeholderResourceTextId: Int? = null,
        withLeadingIcon: Boolean = false,
        padding: Padding = Padding(),
        isFieldOptional: Boolean = false,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default
    ): InputComponentStyle = InputComponentStyle(
        titleStyle = titleTextLabelStyle().apply {
            text = titleText
            textId = titleTextId
        },
        subtitleStyle = subtitleTextLabelStyle().apply {
            text = subtitleText
            textId = subtitleTextId
        },
        inputFieldStyle = inputFieldStyle(withLeadingIcon, keyboardOptions).apply {
            placeholderText = placeholderResourceText
            placeholderTextId = placeholderResourceTextId
        },
        infoStyle = subtitleTextLabelStyle().apply {
            text = infoText
            textId = infoTextId
        },
        errorMessageStyle = errorTextLabelStyle(),
        containerStyle = ContainerStyle(padding = padding),
        isInputFieldOptional = isFieldOptional
    )

    public fun cardSchemeComponentStyle(
        titleText: String = "",
        @StringRes
        titleTextId: Int? = R.string.cko_accepted_cards_title
    ): CardSchemeComponentStyle = CardSchemeComponentStyle(
        titleStyle = subtitleTextLabelStyle().apply {
            text = titleText
            textId = titleTextId
        },
        imageStyle = supportedCardSchemeIconStyle(),
        containerStyle = ContainerStyle(
            margin = Margin(
                top = CardSchemeConstants.marginTop,
                bottom = CardSchemeConstants.marginBottom
            )
        ),
        imageContainerStyle = supportedCardSchemeImageContainerStyle()
    )
}
