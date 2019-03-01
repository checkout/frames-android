package com.checkout.android_sdk.UseCase

import com.checkout.android_sdk.Architecture.UseCase
import com.checkout.android_sdk.Utils.DateFormatter
import java.text.DateFormatSymbols

class GenerateMonthsUseCase(
    private val dateFormatter: DateFormatter,
    private val callback: Callback
) : UseCase {

    override fun execute() {
        val months = DateFormatSymbols().shortMonths
        for (i in 0 until months.size) {
            months[i] = months[i].toUpperCase() + " - " + dateFormatter.formatMonth(i + 1)
        }
        callback.onMonthsGenerated(months)
    }

    interface Callback {
        fun onMonthsGenerated(months: Array<String>)
    }
}
