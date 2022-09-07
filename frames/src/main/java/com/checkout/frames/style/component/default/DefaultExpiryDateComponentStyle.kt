package com.checkout.frames.style.component.default

import com.checkout.frames.R
import com.checkout.frames.style.component.ExpiryDateComponentStyle

public object DefaultExpiryDateComponentStyle {
    public fun light(): ExpiryDateComponentStyle = ExpiryDateComponentStyle(
        DefaultLightStyle.inputComponentStyle(
            titleTextId = R.string.cko_expiry_date_component_title,
            subtitleTextId = R.string.cko_expiry_date_component_subtitle,
            placeholderResourceTextId = R.string.cko_expiry_date_component_placeholder
        )
    )
}
