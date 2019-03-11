package com.checkout.sdk.monthinput

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.date.Month
import com.checkout.sdk.store.InMemoryStore

open class MonthSelectedUseCase(
    open val monthSelectedPosition: Int,
    private val store: InMemoryStore
) : UseCase<Unit> {

    override fun execute() {
        val month = Month.monthFromInteger(monthSelectedPosition + 1)
        store.cardDate = store.cardDate.copy(month = month)
    }
}
