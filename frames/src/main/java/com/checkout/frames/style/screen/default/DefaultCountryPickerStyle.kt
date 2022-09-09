package com.checkout.frames.style.screen.default

import com.checkout.frames.R
import com.checkout.frames.model.Margin
import com.checkout.frames.model.Padding
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.ImageStyle
import com.checkout.frames.style.component.base.InputFieldIndicatorStyle
import com.checkout.frames.style.component.default.DefaultLightStyle
import com.checkout.frames.style.component.default.DefaultTextLabelStyle
import com.checkout.frames.style.screen.CountryPickerStyle
import com.checkout.frames.utils.constants.CountryPickerScreenConstants

public object DefaultCountryPickerStyle {

    public fun light(): CountryPickerStyle {
        val screenTitleStyle = DefaultLightStyle.screenTitleTextLabelStyle(
            Padding(
                CountryPickerScreenConstants.titlePaddingTop,
                CountryPickerScreenConstants.titlePaddingBottom,
                CountryPickerScreenConstants.titlePaddingStart,
                CountryPickerScreenConstants.titlePaddingEnd
            )
        )
        val searchFieldStyle = DefaultLightStyle.inputFieldStyle(true).copy(
            indicatorStyle = InputFieldIndicatorStyle.Border(
                focusedBorderColor = CountryPickerScreenConstants.focusedBorderColor,
                unfocusedBorderColor = CountryPickerScreenConstants.unfocusedBorderColor,
                focusedBorderThickness = CountryPickerScreenConstants.focusedBorderThickness
            ),
            containerStyle = ContainerStyle(
                margin = Margin(
                    start = CountryPickerScreenConstants.margin,
                    end = CountryPickerScreenConstants.margin,
                    bottom = CountryPickerScreenConstants.margin
                )
            ),
            placeholderTextId = R.string.cko_search_county_placeholder,
            leadingIconStyle = ImageStyle(),
            trailingIconStyle = ImageStyle()
        )
        val searchItemStyle = DefaultTextLabelStyle.title(
            fontSize = CountryPickerScreenConstants.searchItemFontSize,
            color = CountryPickerScreenConstants.searchItemFontColor,
            padding = Padding(
                CountryPickerScreenConstants.searchItemPaddingTop,
                CountryPickerScreenConstants.searchItemPaddingBottom,
                CountryPickerScreenConstants.searchItemPaddingStart,
                CountryPickerScreenConstants.searchItemPaddingEnd
            ),
        )

        return CountryPickerStyle(
            screenTitleStyle = screenTitleStyle,
            searchFieldStyle = searchFieldStyle,
            searchItemStyle = searchItemStyle
        )
    }
}
