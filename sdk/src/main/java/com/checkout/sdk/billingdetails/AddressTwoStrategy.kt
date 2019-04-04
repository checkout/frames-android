package com.checkout.sdk.billingdetails

import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.uicommon.TextInputStrategy

class AddressTwoStrategy(private val store: InMemoryStore) : TextInputStrategy {

    override fun getInitialValue(): String {
        return store.billingDetails.addressTwo
    }

    override fun textChanged(text: String) {
        store.billingDetails = store.billingDetails.copy(addressTwo = text)
    }

    override fun focusChanged(text: String, hasFocus: Boolean): Boolean {
        return !hasFocus && text.length < MINIMUM_BILLING_DETAIL_LENGTH
    }

    override fun reset() {
        store.billingDetails = store.billingDetails.copy(addressTwo = "")
    }
}
