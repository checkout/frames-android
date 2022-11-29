package com.checkout.frames.mapper.theme

import com.checkout.base.mapper.Mapper
import com.checkout.frames.style.screen.PaymentFormStyle
import com.checkout.frames.style.theme.PaymentFormTheme

internal class PaymentFormMapper : Mapper<PaymentFormTheme, PaymentFormStyle> {

    override fun map(from: PaymentFormTheme) = PaymentFormStyle(
        PaymentDetailsStyleMapper().map(from),
        BillingFormStyleMapper().map(from)
    )
}
