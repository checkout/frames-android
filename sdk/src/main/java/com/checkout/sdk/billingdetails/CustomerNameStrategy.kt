package com.checkout.sdk.billingdetails

import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.uicommon.TextInputStrategy


class CustomerNameStrategy(private val store: InMemoryStore) : TextInputStrategy {
    override fun execute(text: String) {
        store.customerName = text
    }
}
