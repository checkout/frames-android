package com.checkout.sdk.monthinput

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.date.Month
import com.checkout.sdk.store.InMemoryStore

open class MonthSelectedUseCase(
    open val monthSelectedPosition: Int,
    private val store: InMemoryStore
) : UseCase<Unit> {

    override fun execute() {
        store.cardDate = store.cardDate.copy(month = Month.monthFromInteger(monthSelectedPosition + 1))
    }
}
