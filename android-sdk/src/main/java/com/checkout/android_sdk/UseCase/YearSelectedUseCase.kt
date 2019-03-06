package com.checkout.android_sdk.UseCase

import com.checkout.android_sdk.Architecture.UseCase
import com.checkout.android_sdk.Store.DataStore


class YearSelectedUseCase(
    private val dataStore: DataStore,
    private val years: List<String>,
    private val position: Int
) : UseCase<Unit> {

    override fun execute() {
        dataStore.cardYear = years[position]
    }
}
