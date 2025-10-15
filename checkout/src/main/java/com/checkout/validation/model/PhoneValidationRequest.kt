package com.checkout.validation.model

import com.checkout.base.model.Country

/**
 * A representation of a [PhoneValidationRequest]
 */
@ConsistentCopyVisibility
internal data class PhoneValidationRequest internal constructor(
    /**
     * The phone number.
     */
    val number: String,

    /**
     * The international country calling code. (Required for some risk checks)
     */
    val country: Country?,
)
