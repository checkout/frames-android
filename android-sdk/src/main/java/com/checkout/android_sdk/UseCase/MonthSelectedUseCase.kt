package com.checkout.android_sdk.UseCase

import com.checkout.android_sdk.Architecture.UseCase
import com.checkout.android_sdk.Store.DataStore
import com.checkout.android_sdk.Utils.DateFormatter

class MonthSelectedUseCase(private val dateFormatter: DateFormatter,
                           private val monthSelectedPosition: Int,
                           private val dataStore: DataStore,
                           private val callback: Callback) : UseCase {

    override fun execute() {
        val numberString = dateFormatter.formatMonth(monthSelectedPosition + 1)
        dataStore.cardMonth = numberString
        callback.onMonthSelected(monthSelectedPosition, numberString, true)
    }

    interface Callback {
        fun onMonthSelected(position: Int, numberString: String, finished: Boolean)
    }

}
