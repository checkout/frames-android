package com.checkout.sdk.request

import com.checkout.sdk.billingdetails.NetworkBillingModel

/**
 * The request model object for the card tokenisation request
 */
class CardTokenizationRequest(
    private val number: String,
    private val name: String,
    private val expiryMonth: String,
    private val expiryYear: String,
    private val cvv: String,
    private val billingModel: NetworkBillingModel? = null
)
