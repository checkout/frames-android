package com.checkout.sdk.store

import com.checkout.sdk.cardinput.CardNumber
import com.checkout.sdk.cvvinput.Cvv
import com.checkout.sdk.date.CardDate


open class InMemoryStore(
    open var cardDate: CardDate,
    open var cvv: Cvv,
    open var cardNumber: CardNumber
) {
    private constructor() : this(
        CardDate.UNKNOWN,
        Cvv.UNKNOWN,
        CardNumber.UNKNOWN
    )

    object Factory {
        private val inMemoryStore = InMemoryStore()

        fun get() = inMemoryStore
    }
}
