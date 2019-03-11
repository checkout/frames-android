package com.checkout.sdk.date

import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test
import java.util.*
import java.util.Calendar.YEAR


class CardDateTest {

    @Test
    fun `given month is not known then month is not valid`() {
        val cardDate = CardDate(Month.UNKNOWN, Year(1000))

        assertFalse(cardDate.isMonthValid())
    }

    @Test
    fun `given year is not known then year is not valid`() {
        val cardDate = CardDate(Month.JANUARY, Year(Year.UNKNOWN))

        assertFalse(cardDate.isYearValid())
    }

    @Test
    fun `given month is known then month is valid`() {
        val cardDate = CardDate(Month.MARCH, Year(Year.UNKNOWN))

        assertTrue(cardDate.isMonthValid())
    }

    @Test
    fun `given year before this year then year is invalid`() {
        val cardDate = CardDate(Month.UNKNOWN, Year(1000))

        assertFalse(cardDate.isYearValid())
    }

    @Test
    fun `given year is this year and month is in the past then month is invalid`() {
        val calendar = GregorianCalendar(2020, 5, 3)
        val cardDate = CardDate(Month.JANUARY, Year(calendar.get(YEAR)), calendar)

        assertFalse(cardDate.isMonthValid())
    }

    @Test
    fun `given invalid year then date is invalid`() {
        val cardDate = CardDate(Month.JANUARY, Year(1000))

        assertFalse(cardDate.isDateValid())
    }

    @Test
    fun `given invalid month then date is invalid()`() {
        val calendar = GregorianCalendar(2020, 5, 3)
        val cardDate = CardDate(Month.UNKNOWN, Year(calendar.get(YEAR)), calendar)

        assertFalse(cardDate.isDateValid())
    }

    @Test
    fun `given valid month and year then date is valid()`() {
        val calendar = GregorianCalendar(2020, 5, 3)
        val cardDate = CardDate(Month.DECEMBER, Year(calendar.get(YEAR)), calendar)

        assertTrue(cardDate.isDateValid())
    }
}
