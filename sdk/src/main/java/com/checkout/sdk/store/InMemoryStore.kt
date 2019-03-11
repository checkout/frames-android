package com.checkout.sdk.store

import com.checkout.sdk.monthinput.Month
import com.checkout.sdk.yearinput.Year


open class InMemoryStore(
    open var cardMonth: Month,
    open var cardYear: Year
) {
    private constructor() : this(Month.UNKNOWN, Year(Year.UNKNOWN))

    object Factory {
        private val inMemoryStore = InMemoryStore()

        fun get() = inMemoryStore
    }
}
