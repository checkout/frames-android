package com.checkout.sdk.date

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class MonthTest {

    private val expectedMonthIntegers = mapOf(
        Month.UNKNOWN to 0,
        Month.JANUARY to 1,
        Month.FEBRUARY to 2,
        Month.MARCH to 3,
        Month.APRIL to 4,
        Month.MAY to 5,
        Month.JUNE to 6,
        Month.JULY to 7,
        Month.AUGUST to 8,
        Month.SEPTEMBER to 9,
        Month.OCTOBER to 10,
        Month.NOVEMBER to 11,
        Month.DECEMBER to 12
    )

    @Test
    fun `given month check that the correct value is returned for month integer`() {
        for ((month, expectedInteger) in expectedMonthIntegers.entries) {
            assertEquals(month.monthInteger, expectedInteger)
        }
    }

}
