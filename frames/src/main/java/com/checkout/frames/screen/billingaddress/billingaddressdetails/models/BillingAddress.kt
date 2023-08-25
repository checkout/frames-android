package com.checkout.frames.screen.billingaddress.billingaddressdetails.models

import com.checkout.tokenization.model.Address
import com.checkout.tokenization.model.Phone

internal data class BillingAddress(
    val name: String? = null,
    val address: Address? = null,
    val phone: Phone? = null
) {
    internal constructor() : this(
        "",
        Address("", "", "", "", "")
    )

    internal companion object {
        internal val DEFAULT_BILLING_ADDRESS by lazy { BillingAddress() }

        // Whether the [BillingAddress] has been edited or same as the default one.
        internal fun BillingAddress.isEdited() = (
                name == DEFAULT_BILLING_ADDRESS.name &&
                address?.addressLine1 == DEFAULT_BILLING_ADDRESS.address?.addressLine1 &&
                address?.addressLine2 == DEFAULT_BILLING_ADDRESS.address?.addressLine2 &&
                address?.city == DEFAULT_BILLING_ADDRESS.address?.city &&
                address?.state == DEFAULT_BILLING_ADDRESS.address?.state &&
                address?.zip == DEFAULT_BILLING_ADDRESS.address?.zip &&
                address?.country == DEFAULT_BILLING_ADDRESS.address?.country &&
                phone == DEFAULT_BILLING_ADDRESS.phone
                ).not()
    }
}
