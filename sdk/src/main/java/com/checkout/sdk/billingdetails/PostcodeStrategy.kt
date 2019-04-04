package com.checkout.sdk.billingdetails

import com.checkout.sdk.billingdetails.model.BillingDetail
import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.uicommon.TextInputStrategy


class PostcodeStrategy(private val store: InMemoryStore) : TextInputStrategy {

    override fun getInitialValue(): String {
        return store.billingDetails.postcode.value
    }

    override fun textChanged(text: String) {
        store.billingDetails = store.billingDetails.copy(postcode = BillingDetail(text))
    }

    override fun focusChanged(hasFocus: Boolean): Boolean {
        return !hasFocus && !store.billingDetails.postcode.isValid()
    }

    override fun reset() {
        store.billingDetails = store.billingDetails.copy(postcode = BillingDetail())
    }

}
