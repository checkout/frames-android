package com.checkout.android_sdk.UseCase

import com.checkout.android_sdk.Architecture.UseCase
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class MonthInputPopulateUseCase(private val callback: Callback) : UseCase {

    override fun execute() {
        val months: Array<String> = DateFormatSymbols().shortMonths
        for (i in 0 until months.size) {
            months[i] = months[i].toUpperCase() + " - " + formatMonth(i + 1)
        }
        callback.onMonthsGenerated(months)
    }

    private fun formatMonth(monthInteger: Int): String {
        val monthParse = SimpleDateFormat("MM", Locale.getDefault())
        val monthDisplay = SimpleDateFormat("MM", Locale.getDefault())
        return monthDisplay.format(monthParse.parse(monthInteger.toString()))
    }

    interface Callback {
        fun onMonthsGenerated(months: Array<String>)
    }
}
