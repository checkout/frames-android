package com.checkout.frames.screen.manager

import com.checkout.base.model.CardScheme
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingAddress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal interface PaymentStateManager {

    val cardNumber: MutableStateFlow<String>
    val cardScheme: MutableStateFlow<CardScheme>
    val isCardNumberValid: MutableStateFlow<Boolean>

    val expiryDate: MutableStateFlow<String>
    val isExpiryDateValid: MutableStateFlow<Boolean>

    val cvv: MutableStateFlow<String>
    val isCvvValid: MutableStateFlow<Boolean>

    val supportedCardSchemeList: List<CardScheme>

    val billingAddress: MutableStateFlow<BillingAddress>
    val isBillingAddressValid: MutableStateFlow<Boolean>
    // Whether the billing address form is enabled or set to null
    val isBillingAddressEnabled: MutableStateFlow<Boolean>

    val isReadyForTokenization: StateFlow<Boolean>

    fun resetPaymentState(
        isCvvValid: Boolean,
        isBillingAddressValid: Boolean,
        isBillingAddressEnabled: Boolean,
    )
}
