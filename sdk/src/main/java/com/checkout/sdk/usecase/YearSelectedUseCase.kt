package com.checkout.sdk.usecase

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.store.DataStore


class YearSelectedUseCase(
    private val dataStore: DataStore,
    private val years: List<String>,
    private val position: Int
) : UseCase<Unit> {

    override fun execute() {
        dataStore.cardYear = years[position]
    }
}
