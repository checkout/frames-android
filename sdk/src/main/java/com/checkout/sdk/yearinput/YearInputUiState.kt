package com.checkout.sdk.yearinput

import com.checkout.sdk.architecture.UiState
import java.util.*


data class YearInputUiState(val years: List<String>, val position: Int = 0) : UiState {

    companion object {
        private const val MAX_YEARS_IN_FUTURE = 15

        fun create(calendar: Calendar): YearInputUiState {
            val years = mutableListOf<String>()
            val thisYear = calendar.get(Calendar.YEAR)
            for (i in thisYear until thisYear + MAX_YEARS_IN_FUTURE) {
                years.add(i.toString())
            }
            return YearInputUiState(years)
        }
    }
}
