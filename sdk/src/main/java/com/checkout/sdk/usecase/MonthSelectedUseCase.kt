package com.checkout.sdk.usecase

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.store.DataStore
import com.checkout.sdk.utils.DateFormatter

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
