package com.checkout.sdk.billingdetails.strategy

import com.checkout.sdk.billingdetails.model.BillingDetail
import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.uicommon.TextInputStrategy


class AddressOneStrategy(private val store: InMemoryStore) : TextInputStrategy {

    override fun getInitialValue(): String {
        return store.billingDetails.addressOne.value
    }

    override fun textChanged(text: String) {
        store.billingDetails = store.billingDetails.copy(addressOne = BillingDetail(text))
    }

    override fun focusChanged(hasFocus: Boolean): Boolean {
        return !hasFocus && !store.billingDetails.addressOne.isValid()
    }

    override fun reset() {
        store.billingDetails = store.billingDetails.copy(addressOne = BillingDetail(""))
    }
}
