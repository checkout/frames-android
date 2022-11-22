package com.checkout.frames.style.theme

public data class PaymentFormTheme(
    /*
    Custom colors provided by the merchants
    */
    val paymentFormThemeColors: PaymentFormThemeColors,
    /*
    Custom CornerRadius provided by the merchants
    */
    val paymentFormCornerRadius: PaymentFormCornerRadius = PaymentFormCornerRadius(),
    /*
    Custom Shape provided by the merchants
    */
    val paymentFormShape: PaymentFormShape = PaymentFormShape(),
    /*
    Custom Font provided by the merchants
    */
    val paymentFormFont: PaymentFormFont = PaymentFormFont()
)
