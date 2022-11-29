package com.checkout.frames.style.theme

public data class PaymentFormTheme(
    /**
     * Custom colors provided by the merchants
     */
    val paymentFormThemeColors: PaymentFormThemeColors,
    /**
     * Custom payment form fields used by the merchants
     */
    val paymentFormComponents: List<PaymentFormComponent>,
    /**
     * Custom CornerRadius provided by the merchants
     */
    val paymentFormCornerRadius: PaymentFormCornerRadius = PaymentFormCornerRadius(),
    /**
     * Custom Shape provided by the merchants
     */
    val paymentFormShape: PaymentFormShape = PaymentFormShape(),
    /**
     * Custom Font provided by the merchants
     */
    val paymentFormFont: PaymentFormFont = PaymentFormFont()
)
