package com.checkout.sdk.billingdetails

import com.checkout.sdk.billingdetails.model.BillingDetail
import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.uicommon.TextInputStrategy

class AddressTwoStrategy(private val store: InMemoryStore) : TextInputStrategy {

    override fun getInitialValue(): String {
        return store.billingDetails.addressTwo.value
    }

    override fun textChanged(text: String) {
        store.billingDetails = store.billingDetails.copy(addressTwo = BillingDetail(text))
    }

    override fun focusChanged(hasFocus: Boolean): Boolean {
        return !hasFocus && !store.billingDetails.addressTwo.isValid()
    }

    override fun reset() {
        store.billingDetails = store.billingDetails.copy(addressTwo = BillingDetail(""))
    }
}
