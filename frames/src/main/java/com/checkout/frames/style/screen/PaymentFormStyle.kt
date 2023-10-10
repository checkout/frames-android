package com.checkout.frames.style.screen

public data class PaymentFormStyle @JvmOverloads constructor(
    public var paymentDetailsStyle: PaymentDetailsStyle = PaymentDetailsStyle(),
    public var billingFormStyle: BillingFormStyle = BillingFormStyle(),
)
