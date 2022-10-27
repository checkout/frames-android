package com.checkout.frames.style.screen

import com.checkout.frames.style.screen.default.DefaultBillingAddressDetailsStyle

public data class PaymentFormStyle(
    public var paymentDetailsStyle: PaymentDetailsStyle = PaymentDetailsStyle(),
    public var billingFormStyle: BillingFormStyle = DefaultBillingAddressDetailsStyle.light()
)
