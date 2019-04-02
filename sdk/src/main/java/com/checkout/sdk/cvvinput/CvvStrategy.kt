package com.checkout.sdk.cvvinput

import com.checkout.sdk.core.TextInputStrategy
import com.checkout.sdk.store.InMemoryStore


class CvvStrategy(private val store: InMemoryStore) :
    TextInputStrategy {

    override fun execute(text: String) {
        store.cvv = store.cvv.copy(value = text)
    }
}
