package com.checkout.frames.style.theme.paymentform

import com.checkout.frames.style.screen.PaymentFormStyle
import com.checkout.frames.style.theme.PaymentFormTheme

public object PaymentFormStyleProvider {
    public fun provide(theme: PaymentFormTheme): PaymentFormStyle = PaymentFormStyle(
        PaymentDetailsStyleTheme.providePaymentStyle(theme),
        BillingFormTheme.provideBillingAddressDetailsStyle(theme)
    )
}
