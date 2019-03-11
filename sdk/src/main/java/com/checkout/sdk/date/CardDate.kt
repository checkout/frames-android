package com.checkout.sdk.date

import java.util.*


data class CardDate(
    val month: Month,
    val year: Year,
    private val calendar: Calendar = Calendar.getInstance()
) {

    /**
     * Returns a boolean showing if the month is valid
     */
    fun isMonthValid(): Boolean {
        val calendarYear = calendar.get(Calendar.YEAR)
        val calendarMonth = calendar.get(Calendar.MONTH)

        if (!month.isKnown() || (year.value == calendarYear && month.monthInteger < calendarMonth)) {
            return false
        }
        return true
    }

    /**
     * Returns a boolean showing if the year is valid
     */
    fun isYearValid(): Boolean {
        val calendarYear = calendar.get(Calendar.YEAR)

        if (!year.isKnown() || year.value < calendarYear) {
            return false
        }
        return true
    }

    /**
     * Returns a boolean showing if the date is valid
     */
    fun isDateValid(): Boolean {
        return isMonthValid() && isYearValid()
    }

    companion object {
        val UNKNOWN = CardDate(Month.UNKNOWN, Year.UNKNOWN)
    }
}
