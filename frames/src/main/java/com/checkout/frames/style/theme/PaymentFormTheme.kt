package com.checkout.frames.style.theme

public data class PaymentFormTheme(
    /**
     * Custom colors provided by the merchants
     */
    val paymentFormThemeColors: PaymentFormThemeColors,
    /**
     * Custom payment form fields used by the merchants
     */
    val paymentFormComponents: List<PaymentFormComponent> = DefaultPaymentFormTheme.provideComponents(),
    /**
     * Custom Shape provided by the merchants
     */
    val paymentFormShape: PaymentFormShape,
    /**
     * Custom CornerRadius provided by the merchants
     */
    val paymentFormCornerRadius: PaymentFormCornerRadius,
    /**
     * Custom Font provided by the merchants
     */
    val paymentFormFont: PaymentFormFont,
) {
    public constructor(
        paymentFormThemeColors: PaymentFormThemeColors,
        paymentFormComponents: List<PaymentFormComponent>,
    ) : this(
        paymentFormThemeColors,
        paymentFormComponents,
        PaymentFormShape(),
        PaymentFormCornerRadius(),
        PaymentFormFont()
    )
}
