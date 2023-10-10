package com.checkout.frames.style.component.default

import com.checkout.frames.R
import com.checkout.frames.model.Padding
import com.checkout.frames.style.component.CountryComponentStyle
import com.checkout.frames.style.component.base.ImageStyle
import com.checkout.frames.style.component.base.InputFieldIndicatorStyle
import com.checkout.frames.utils.constants.LightStyleConstants

public object DefaultCountryComponentStyle {
    public fun light(): CountryComponentStyle {
        val inputFieldStyle = DefaultLightStyle.inputFieldStyle()
            .copy(
                indicatorStyle = InputFieldIndicatorStyle.Border(
                    focusedBorderColor = LightStyleConstants.unfocusedBorderColor,
                    unfocusedBorderColor = LightStyleConstants.unfocusedBorderColor,
                    disabledBorderColor = LightStyleConstants.unfocusedBorderColor,
                ),
                trailingIconStyle = ImageStyle(image = R.drawable.cko_ic_caret_right),
            )

        return CountryComponentStyle(
            DefaultLightStyle.inputComponentStyle(
                titleTextId = R.string.cko_country_picker_screen_title,
                padding = Padding(
                    start = LightStyleConstants.inputComponentLeftPadding,
                    end = LightStyleConstants.inputComponentRightPadding,
                    bottom = LightStyleConstants.inputComponentBottomPadding,
                ),
            )
                .copy(inputFieldStyle = inputFieldStyle),
        )
    }
}
