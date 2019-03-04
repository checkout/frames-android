package com.checkout.android_sdk.UseCase

import com.checkout.android_sdk.Architecture.UseCase
import com.checkout.android_sdk.Store.DataStore


class YearSelectedUseCase(
    private val dataStore: DataStore,
    private val years: List<String>,
    private val position: Int,
    private val callback: Callback
) : UseCase {

    override fun execute() {
        dataStore.cardYear = years[position]
        callback.onYearSelected(position)
    }

    interface Callback {
        fun onYearSelected(position: Int)
    }
}
