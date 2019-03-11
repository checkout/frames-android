package com.checkout.sdk.yearinput

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.store.InMemoryStore


open class YearResetUseCase(private val store: InMemoryStore): UseCase<Unit> {

    override fun execute() {
        store.cardYear = Year(Year.UNKNOWN)
    }
}
