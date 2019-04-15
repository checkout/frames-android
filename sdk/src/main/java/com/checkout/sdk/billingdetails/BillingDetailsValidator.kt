package com.checkout.sdk.billingdetails

import com.checkout.sdk.store.InMemoryStore


class BillingDetailsValidator(private val store: InMemoryStore) {

    fun getValidity(): BillingDetailsValidity {
        val billingDetails = store.billingDetails
        return BillingDetailsValidity(
            store.customerName.isValid(),
            billingDetails.addressOne.isValid(),
            billingDetails.addressTwo.isValid(),
            billingDetails.city.isValid(),
            billingDetails.state.isValid(),
            billingDetails.postcode.isValid(),
            !billingDetails.country.isEmpty(),
            billingDetails.phone.isValid()
        )
    }

    fun isValid(): Boolean {
        return getValidity().areDetailsValid()
    }
}
