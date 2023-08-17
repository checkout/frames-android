package com.checkout.frames.screen.paymentform.model

import com.checkout.tokenization.model.Address
import com.checkout.tokenization.model.Phone

/**
 * @param name - represent the cardHolderName in Billing form
 * @param address - [Address] represent the payment source owner's billing address in Billing form
 * @param phone - [Phone] represent the payment source owner's billing phone in Billing form
 */
public data class BillingFormAddress @JvmOverloads constructor(
    val name: String? = null,
    val address: Address? = null,
    val phone: Phone? = null,
)
