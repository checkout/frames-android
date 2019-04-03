package com.checkout.sdk.cvvinput

import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.uicommon.TextInputStrategy


class CvvStrategy(private val store: InMemoryStore) : TextInputStrategy {

    override fun textChanged(text: String) {
        store.cvv = store.cvv.copy(value = text)
    }

    override fun focusChanged(text: String, hasFocus: Boolean): Boolean {
        return !hasFocus && text.length != store.cvv.expectedLength
    }
}
