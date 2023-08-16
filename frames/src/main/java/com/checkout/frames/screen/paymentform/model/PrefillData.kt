package com.checkout.frames.screen.paymentform.model

/**
 * @param cardHolderName - represent the cardHolderName in PaymentDetail form
 * @param billingFormAddress - [BillingFormAddress] represent the payment source owner's full billing address
 */
public data class PrefillData @JvmOverloads constructor(
    public val cardHolderName: String? = null,
    public val billingFormAddress: BillingFormAddress? = null
)
