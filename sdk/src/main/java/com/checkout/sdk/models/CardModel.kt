package com.checkout.sdk.models

import com.checkout.sdk.billingdetails.model.BillingDetails

/**
 * Http request card details object model
 */
class CardModel {

    val expiryMonth: String? = null
    val expiryYear: String? = null
    val billingDetails: BillingDetails? = null
    val id: String? = null
    val last4: String? = null
    val bin: String? = null
    val paymentMethod: String? = null
    val name: String? = null
}
