package com.checkout.tokenization.model

import com.checkout.base.model.Country

/**
 * A representation of a [Address]
 */
public data class Address constructor(

    /**
     * Address line 1 (Street address/PO Box/Company name)
     */
    val addressLine1: String,

    /**
     * Address line 2 (Apartment/Suite/Unit/Building)
     */
    val addressLine2: String,

    /**
     * City/District/Suburb/Town/Village
     */
    val city: String,

    /**
     * State/County/Province/Region
     */
    val state: String,

    /**
     * ZIP or postal code
     */
    val zip: String,

    /**
     * Billing address country [Country]
     */
    var country: Country
)
