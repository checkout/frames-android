package com.checkout.android_sdk.UseCase

import com.checkout.android_sdk.Architecture.UseCase
import com.checkout.android_sdk.Store.DataStore
import com.checkout.android_sdk.Utils.DateFormatter

class MonthSelectedUseCase(
    private val dateFormatter: DateFormatter,
    private val monthSelectedPosition: Int,
    private val dataStore: DataStore
) : UseCase<MonthSelectedUseCase.MonthSelectedResult> {

    override fun execute(): MonthSelectedResult {
        val numberString = dateFormatter.formatMonth(monthSelectedPosition + 1)
        dataStore.cardMonth = numberString
        return MonthSelectedResult(monthSelectedPosition, numberString, true)
    }

    data class MonthSelectedResult(
        val position: Int,
        val numberString: String,
        val finished: Boolean
    )
}
