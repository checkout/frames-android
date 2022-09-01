package com.checkout.frames.style.component.default

import com.checkout.frames.R
import com.checkout.frames.model.Padding
import com.checkout.frames.style.component.CardNumberComponentStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.style.component.base.TextStyle
import com.checkout.frames.style.component.base.InputFieldIndicatorStyle
import com.checkout.frames.style.component.base.ImageStyle
import com.checkout.frames.utils.constants.CardNumberComponentConstants

public object DefaultCardNumberComponentStyle {

    public fun light(): CardNumberComponentStyle {
        val titleStyle = DefaultTextLabelStyle.title()
            .apply { textId = R.string.cko_card_number_title }
        val style = InputComponentStyle(
            titleStyle = titleStyle,
            inputFieldStyle = InputFieldStyle(
                textStyle = TextStyle(
                    size = CardNumberComponentConstants.fontSize,
                    color = CardNumberComponentConstants.fontColor,
                    font = CardNumberComponentConstants.font,
                    maxLines = CardNumberComponentConstants.maxLines
                ),
                indicatorStyle = InputFieldIndicatorStyle.Border(
                    unfocusedBorderColor = CardNumberComponentConstants.unfocusedBorderColor,
                    focusedBorderColor = CardNumberComponentConstants.focusedBorderColor,
                    errorBorderColor = CardNumberComponentConstants.errorBorderColor
                ),
                leadingIconStyle = ImageStyle(
                    padding = Padding(
                        start = CardNumberComponentConstants.leadingIconStartPadding,
                        end = CardNumberComponentConstants.leadingIconEndPadding
                    ),
                    height = CardNumberComponentConstants.leadingIconHeight,
                    width = CardNumberComponentConstants.leadingIconWidth
                )
            )
        )

        return CardNumberComponentStyle(style, CardNumberComponentConstants.cardNumberSeparator)
    }
}
