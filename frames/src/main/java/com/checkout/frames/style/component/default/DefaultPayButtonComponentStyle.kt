package com.checkout.frames.style.component.default

import com.checkout.frames.R
import com.checkout.frames.style.component.PayButtonComponentStyle
import com.checkout.frames.utils.constants.PaymentButtonConstants

public object DefaultPayButtonComponentStyle {

    public fun light(): PayButtonComponentStyle = PayButtonComponentStyle(
        buttonStyle = DefaultButtonStyle.lightSolid(
            textId = R.string.cko_pay,
            contentColor = PaymentButtonConstants.contentColor,
            containerColor = PaymentButtonConstants.containerColor,
            disabledContentColor = PaymentButtonConstants.disabledContentColor,
            disabledContainerColor = PaymentButtonConstants.disabledContainerColor,
            contentPadding = PaymentButtonConstants.contentPadding,
            fontWeight = PaymentButtonConstants.fontWeight,
            margin = PaymentButtonConstants.buttonMargin,
        ),
    )
}
