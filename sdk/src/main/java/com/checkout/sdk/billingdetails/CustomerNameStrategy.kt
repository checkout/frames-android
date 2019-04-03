package com.checkout.sdk.billingdetails

import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.uicommon.TextInputStrategy


class CustomerNameStrategy(private val store: InMemoryStore) : TextInputStrategy {

    override fun textChanged(text: String) {
        store.customerName = text
    }

    override fun focusChanged(text: String, hasFocus: Boolean): Boolean {
        return !hasFocus && text.length < MINIMUM_CUSTOMER_NAME_LENGTH
    }

    override fun reset() {
        store.customerName = ""
    }

    companion object {
        private const val MINIMUM_CUSTOMER_NAME_LENGTH = 3
    }
}
