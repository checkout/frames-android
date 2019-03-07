package com.checkout.sdk.store


open class InMemoryStore(
    open var cardMonth: Int?
) {
    private constructor() : this(null)

    object Factory {
        private val inMemoryStore = InMemoryStore()

        fun get() = inMemoryStore
    }
}
