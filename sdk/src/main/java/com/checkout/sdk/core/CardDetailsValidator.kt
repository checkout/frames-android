package com.checkout.sdk.core

import com.checkout.sdk.store.InMemoryStore


class CardDetailsValidator(private val store: InMemoryStore) {

    fun getValidity(): CardDetailsValidity {
        return CardDetailsValidity(
            store.cardNumber.isValid(),
            store.cvv.isValid(),
            store.cardDate.isMonthValid(),
            store.cardDate.isYearValid()
        )
    }
}
