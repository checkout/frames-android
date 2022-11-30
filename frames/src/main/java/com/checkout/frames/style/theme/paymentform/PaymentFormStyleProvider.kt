package com.checkout.frames.style.theme.paymentform

import com.checkout.frames.mapper.theme.BillingFormStyleMapper
import com.checkout.frames.mapper.theme.PaymentDetailsStyleMapper
import com.checkout.frames.mapper.theme.PaymentFormMapper
import com.checkout.frames.style.screen.PaymentFormStyle
import com.checkout.frames.style.theme.PaymentFormTheme

public object PaymentFormStyleProvider {
    public fun provide(theme: PaymentFormTheme): PaymentFormStyle = PaymentFormMapper(
        PaymentDetailsStyleMapper(), BillingFormStyleMapper()
    ).map(theme)
}
