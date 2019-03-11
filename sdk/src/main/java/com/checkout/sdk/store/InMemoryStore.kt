package com.checkout.sdk.store

import com.checkout.sdk.monthinput.Month


open class InMemoryStore(
    open var cardMonth: Month
) {
    private constructor() : this(Month.UNKNOWN)

    object Factory {
        private val inMemoryStore = InMemoryStore()

        fun get() = inMemoryStore
    }
}
