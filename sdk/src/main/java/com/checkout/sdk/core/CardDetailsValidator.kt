package com.checkout.sdk.core

import com.checkout.sdk.store.InMemoryStore


open class CardDetailsValidator(private val store: InMemoryStore) {

    open fun getValidity(): CardDetailsValidity {
        return CardDetailsValidity(
            store.cardNumber.isValid(),
            store.cvv.isValid(),
            store.cardDate.isMonthValid(),
            store.cardDate.isYearValid()
        )
    }
}
