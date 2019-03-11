package com.checkout.sdk.store

import com.checkout.sdk.date.CardDate
import com.checkout.sdk.date.Month
import com.checkout.sdk.date.Year


open class InMemoryStore(
    open var cardDate: CardDate
) {
    private constructor() : this(CardDate(Month.UNKNOWN, Year(Year.UNKNOWN)))

    object Factory {
        private val inMemoryStore = InMemoryStore()

        fun get() = inMemoryStore
    }
}
