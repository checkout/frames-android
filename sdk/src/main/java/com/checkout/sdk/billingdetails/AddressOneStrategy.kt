package com.checkout.sdk.billingdetails

import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.uicommon.TextInputStrategy


class AddressOneStrategy(private val store: InMemoryStore) : TextInputStrategy {

    override fun getInitialValue(): String {
        return store.addressOne
    }

    override fun textChanged(text: String) {
        store.addressOne = text
    }

    override fun focusChanged(text: String, hasFocus: Boolean): Boolean {
        return !hasFocus && text.length < MINIMUM_BILLING_DETAIL_LENGTH
    }

    override fun reset() {
        store.addressOne = ""
    }
}
