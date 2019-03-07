package com.checkout.sdk.monthinput

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.store.InMemoryStore

open class MonthSelectedUseCase(
    open val monthSelectedPosition: Int,
    private val store: InMemoryStore
) : UseCase<Unit> {

    override fun execute() {
        store.cardMonth = monthSelectedPosition + 1
    }
}
