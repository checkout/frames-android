package com.checkout.sdk.store

import com.checkout.sdk.cardinput.CardNumber
import com.checkout.sdk.cvvinput.Cvv
import com.checkout.sdk.date.CardDate
import com.checkout.sdk.models.BillingModel


open class InMemoryStore(
    open var cardDate: CardDate,
    open var cvv: Cvv,
    open var cardNumber: CardNumber,
    open var customerName: String,
    open var billingDetails: BillingModel
) {
    private constructor() : this(
        CardDate.UNKNOWN,
        Cvv.UNKNOWN,
        CardNumber.UNKNOWN,
        "",
        BillingModel()
    )

    object Factory {
        private val inMemoryStore = InMemoryStore()

        fun get() = inMemoryStore
    }
}
