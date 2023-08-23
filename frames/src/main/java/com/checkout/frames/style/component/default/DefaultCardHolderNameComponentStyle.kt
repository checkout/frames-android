package com.checkout.frames.style.component.default

import com.checkout.frames.R
import com.checkout.frames.model.Margin
import com.checkout.frames.style.component.CardHolderNameComponentStyle
import com.checkout.frames.utils.constants.PaymentDetailsScreenConstants.marginBottom

public object DefaultCardHolderNameComponentStyle {

    public fun light(isOptional: Boolean = true): CardHolderNameComponentStyle {
        val style = DefaultLightStyle.inputComponentStyle(
            titleTextId = R.string.cko_card_holder_name_title,
            infoTextId = if (isOptional) R.string.cko_input_field_optional_info else null,
            margin = Margin(bottom = marginBottom),
            isFieldOptional = isOptional
        )
        return CardHolderNameComponentStyle(style)
    }
}
