package com.checkout.sdk.billingdetails

import com.checkout.sdk.billingdetails.model.BillingDetail
import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.uicommon.TextInputStrategy


class StateStrategy(private val store: InMemoryStore) : TextInputStrategy {

    override fun getInitialValue(): String {
        return store.billingDetails.state.value
    }

    override fun textChanged(text: String) {
        store.billingDetails = store.billingDetails.copy(state = BillingDetail(text))
    }

    override fun focusChanged(hasFocus: Boolean): Boolean {
        return !hasFocus && !store.billingDetails.state.isValid()
    }

    override fun reset() {
        store.billingDetails = store.billingDetails.copy(state = BillingDetail())
    }

}

