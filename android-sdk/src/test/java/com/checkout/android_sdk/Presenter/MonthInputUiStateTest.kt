package com.checkout.android_sdk.Presenter

import com.checkout.android_sdk.Utils.DateFormatter
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MonthInputUiStateTest {

    @Mock
    private lateinit var dateFormatter: DateFormatter

    @Test
    fun `given new months generated then view should be updated with values for months`() {
        prepareFormatter()
        val monthInputState = MonthInputPresenter.MonthInputUiState.create(dateFormatter)

        assertEquals(getExpectedMonths().asList(), monthInputState.months)
    }

    private fun prepareFormatter() {
        for (i in 0 .. 12) {
            var iAsString = i.toString()
            if (iAsString.length < 2) {
                iAsString = "0$iAsString"
            }
            BDDMockito.given(dateFormatter.formatMonth(i)).willReturn(iAsString)
        }
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
            " - null" // JVM adds this additional entry not present on Android OS
        )
    }
}
