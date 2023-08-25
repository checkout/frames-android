package com.checkout.tokenization.model

import com.checkout.base.model.Country

/**
 * A representation of a [Phone]
 */
public data class Phone constructor(
    /**
     * The phone number.
     */
    val number: String,

    /**
     * The international country calling code. (Required for some risk checks)
     */
    val country: Country?
)
