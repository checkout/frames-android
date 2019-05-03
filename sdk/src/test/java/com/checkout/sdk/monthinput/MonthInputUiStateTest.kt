package com.checkout.sdk.monthinput

import com.checkout.sdk.utils.DateFormatter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.willAnswer
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
class MonthInputUiStateTest {

    @Mock
    private lateinit var dateFormatter: DateFormatter

    @Test
    fun `month input state should be 12 months january to december followed by month number`() {
        prepareFormatter()
        val monthInputState = MonthInputUiState.create(dateFormatter)

        assertEquals(getExpectedMonths().asList(), monthInputState.months)
    }

    private fun prepareFormatter() {
        willAnswer {
            var iAsString = it.getArgument<Int>(0).toString()
            if (iAsString.length < 2) {
                iAsString = "0$iAsString"
            }
            iAsString
        }.given(dateFormatter).formatMonth(any(Int::class.java))
    }

    private fun getExpectedMonths(): Array<String> {
        return arrayOf(
            "JAN - 01",
            "FEB - 02",
            "MAR - 03",
            "APR - 04",
            "MAY - 05",
            "JUN - 06",
            "JUL - 07",
            "AUG - 08",
            "SEP - 09",
            "OCT - 10",
            "NOV - 11",
            "DEC - 12",
            " - 13" // JVM adds this additional entry not present on Android OS
        )
    }
}
