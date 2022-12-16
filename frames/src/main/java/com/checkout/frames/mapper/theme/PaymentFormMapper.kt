package com.checkout.frames.mapper.theme

import com.checkout.base.mapper.Mapper
import com.checkout.frames.style.screen.BillingFormStyle
import com.checkout.frames.style.screen.PaymentDetailsStyle
import com.checkout.frames.style.screen.PaymentFormStyle
import com.checkout.frames.style.theme.PaymentFormTheme

internal class PaymentFormMapper(
    private val paymentDetailsStyleMapper: Mapper<PaymentFormTheme, PaymentDetailsStyle>,
    private val billingFormStyleMapper: Mapper<PaymentFormTheme, BillingFormStyle>
) : Mapper<PaymentFormTheme, PaymentFormStyle> {

    override fun map(from: PaymentFormTheme) = PaymentFormStyle(
        paymentDetailsStyleMapper.map(from),
        billingFormStyleMapper.map(from)
    )
}
