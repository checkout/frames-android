package com.checkout.sdk.UseCase

import com.checkout.sdk.Architecture.UseCase
import com.checkout.sdk.Store.DataStore
import com.checkout.sdk.Utils.DateFormatter

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
