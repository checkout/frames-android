package com.checkout.sdk.monthinput

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.store.InMemoryStore


open class MonthResetUseCase(private val store: InMemoryStore): UseCase<Unit> {

    override fun execute() {
        store.cardMonth = Month.UNKNOWN
    }
}
