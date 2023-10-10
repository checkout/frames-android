package com.checkout.frames.style.component.default

import com.checkout.frames.R
import com.checkout.frames.model.Margin
import com.checkout.frames.style.component.ExpiryDateComponentStyle
import com.checkout.frames.utils.constants.PaymentDetailsScreenConstants

public object DefaultExpiryDateComponentStyle {
    public fun light(): ExpiryDateComponentStyle = ExpiryDateComponentStyle(
        DefaultLightStyle.inputComponentStyle(
            titleTextId = R.string.cko_expiry_date_component_title,
            subtitleTextId = R.string.cko_expiry_date_component_subtitle,
            placeholderResourceTextId = R.string.cko_expiry_date_component_placeholder,
            margin = Margin(bottom = PaymentDetailsScreenConstants.marginBottom),
        ),
    )
}
