package com.checkout.tokenization.model

import com.checkout.base.model.Country

/**
 * A representation of a [Address]
 */
public data class Address constructor(

    /**
     * Address line 1 (Street address/PO Box/Company name)
     */
    var addressLine1: String,

    /**
     * Address line 2 (Apartment/Suite/Unit/Building)
     */
    var addressLine2: String,

    /**
     * City/District/Suburb/Town/Village
     */
    var city: String,

    /**
     * State/County/Province/Region
     */
    var state: String,

    /**
     * ZIP or postal code
     */
    var zip: String,

    /**
     * Billing address country [Country]
     */
    var country: Country
)
