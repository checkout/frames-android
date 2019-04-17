package com.checkout.sdk.cvvinput

import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.uicommon.TextInputStrategy


class CvvStrategy(private val store: InMemoryStore) : TextInputStrategy {

    override fun textChanged(text: String) {
        store.cvv = store.cvv.copy(value = text)
    }

    override fun focusChanged(hasFocus: Boolean): Boolean {
        return !hasFocus && !store.cvv.isValid()
    }

    override fun reset() {
        store.cvv = Cvv.UNKNOWN
    }
}
