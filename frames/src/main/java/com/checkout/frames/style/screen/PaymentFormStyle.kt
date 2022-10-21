package com.checkout.frames.style.screen

import com.checkout.frames.style.screen.default.DefaultBillingFormStyle

public data class PaymentFormStyle(
    public var paymentDetailsStyle: PaymentDetailsStyle = PaymentDetailsStyle(),
    public var billingFormStyle: BillingFormStyle = DefaultBillingFormStyle.light()
)
