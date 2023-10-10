package com.checkout.frames.style.component.default

import com.checkout.frames.R
import com.checkout.frames.model.Margin
import com.checkout.frames.style.component.CvvComponentStyle
import com.checkout.frames.utils.constants.PaymentDetailsScreenConstants

public object DefaultCvvComponentStyle {
    public fun light(): CvvComponentStyle = CvvComponentStyle(
        DefaultLightStyle.inputComponentStyle(
            titleTextId = R.string.cko_cvv_component_title,
            subtitleTextId = R.string.cko_cvv_component_subtitle,
            margin = Margin(bottom = PaymentDetailsScreenConstants.marginBottom),
        ),
    )
}
