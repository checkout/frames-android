package com.checkout.sdk.billingdetails.strategy

import com.checkout.sdk.billingdetails.model.BillingDetail
import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.uicommon.TextInputStrategy


class CustomerNameStrategy(private val store: InMemoryStore) : TextInputStrategy {

    override fun getInitialValue(): String {
        return store.customerName.value
    }

    override fun textChanged(text: String) {
        store.customerName = store.customerName.copy(value = text)
    }

    override fun focusChanged(hasFocus: Boolean): Boolean {
        return !hasFocus && !store.customerName.isValid()
    }

    override fun reset() {
        store.customerName = BillingDetail()
    }
}
