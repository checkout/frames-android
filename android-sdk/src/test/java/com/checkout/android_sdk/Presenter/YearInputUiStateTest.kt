package com.checkout.android_sdk.Presenter


import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class YearInputUiStateTest {

    @Test
    fun `year input should be a list of every year for the next 15 years`() {
        val expectedYears = listOf("2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034")
        val calendar = GregorianCalendar(2020, 6, 1)
        val yearInputState = YearInputPresenter.YearInputUiState.create(calendar)

        assertEquals(expectedYears, yearInputState.years)
    }
}
