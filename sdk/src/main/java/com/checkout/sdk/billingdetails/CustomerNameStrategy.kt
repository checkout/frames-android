package com.checkout.sdk.billingdetails

import com.checkout.sdk.core.TextInputStrategy
import com.checkout.sdk.store.InMemoryStore


class CustomerNameStrategy(private val store: InMemoryStore) : TextInputStrategy {
    override fun execute(text: String) {
        store.customerName = text
    }
}
