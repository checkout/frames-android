package com.checkout.sdk.yearinput

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.date.Year
import com.checkout.sdk.store.InMemoryStore


open class YearSelectedUseCase(
    private val store: InMemoryStore,
    private val position: Int,
    private val years: List<String>
) : UseCase<Unit> {

    private constructor(builder: Builder) : this(
        builder.store,
        builder.position,
        builder.years
    )

    override fun execute() {
        store.cardDate = store.cardDate.copy(year = Year(Integer.parseInt(years[position])))
    }

    open class Builder(val store: InMemoryStore, open val position: Int) {
        lateinit var years: List<String>
            private set

        open fun years(years: List<String>) = apply { this.years = years }

        open fun build() = YearSelectedUseCase(this)
    }
}
