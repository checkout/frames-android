package com.checkout.sdk.core

import com.checkout.sdk.store.InMemoryStore


class RequestValidator(private val store: InMemoryStore) {

    fun getRequestValidity(): RequestValidity {
        return RequestValidity(
            store.cardNumber.isValid(),
            store.cvv.isValid(),
            store.cardDate.isMonthValid(),
            store.cardDate.isYearValid()
        )
    }
}
