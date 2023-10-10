package com.checkout.frames.style.component.default

import com.checkout.frames.R
import com.checkout.frames.model.Padding
import com.checkout.frames.style.component.base.ImageStyle
import com.checkout.frames.utils.constants.ButtonStyleConstants
import com.checkout.frames.utils.constants.LightStyleConstants

public object DefaultImageStyle {

    @JvmOverloads
    public fun buttonTrailingImageStyle(
        image: Int = R.drawable.cko_ic_caret_right,
        tinColor: Long? = LightStyleConstants.focusedBorderColor,
        height: Int? = ButtonStyleConstants.trailingIconHeight,
        width: Int? = null,
        padding: Padding? = ButtonStyleConstants.trailingIconPadding,
    ): ImageStyle {
        return ImageStyle(
            image = image,
            tinColor = tinColor,
            height = height,
            width = width,
            padding = padding,
        )
    }
}
