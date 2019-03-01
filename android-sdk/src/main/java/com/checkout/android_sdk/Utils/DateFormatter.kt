package com.checkout.android_sdk.Utils

import java.text.SimpleDateFormat
import java.util.*


class DateFormatter {

    /**
     * Turn the month integer into a formatted String: 1 -> 01 etc
     */
    fun formatMonth(monthInteger: Int): String {
        val monthParse = SimpleDateFormat("MM", Locale.getDefault())
        val monthDisplay = SimpleDateFormat("MM", Locale.getDefault())
        return monthDisplay.format(monthParse.parse(monthInteger.toString()))
    }

}
