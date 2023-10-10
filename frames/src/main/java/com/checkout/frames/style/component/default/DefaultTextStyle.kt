package com.checkout.frames.style.component.default

import com.checkout.frames.style.component.base.TextStyle
import com.checkout.frames.utils.constants.PlaceHolderConstants

public object DefaultTextStyle {

    public fun placeHolder(): TextStyle = TextStyle(
        size = PlaceHolderConstants.fontSize,
        color = PlaceHolderConstants.fontColor,
        font = PlaceHolderConstants.font,
        maxLines = PlaceHolderConstants.maxLines,
    )
}
