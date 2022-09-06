package com.checkout.frames.style.component.default

import com.checkout.frames.R
import com.checkout.frames.style.component.CvvComponentStyle

public object DefaultCvvComponentStyle {
    public fun light(): CvvComponentStyle = CvvComponentStyle(
        DefaultLightStyle.inputComponentStyle(
            titleTextId = R.string.cko_cvv_component_title,
            subtitleTextId = R.string.cko_cvv_component_subtitle
        )
    )
}
