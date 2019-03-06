package com.checkout.sdk.yearinput

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.store.DataStore


open class YearSelectedUseCase(
    private val dataStore: DataStore,
    private val position: Int,
    private val years: List<String>
) : UseCase<Unit> {

    private constructor(builder: Builder) : this(
        builder.dataStore,
        builder.getPosition(),
        builder.years
    )

    override fun execute() {
        dataStore.cardYear = years[position]
    }

    open class Builder(val dataStore: DataStore, private val position: Int) {
        lateinit var years: List<String>
            private set

        open fun years(years: List<String>) = apply { this.years = years }

        open fun getPosition() = position

        open fun build() = YearSelectedUseCase(this)
    }
}
