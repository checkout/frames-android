package com.checkout.sdk.yearinput


import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*


@ExtendWith(MockitoExtension::class)
class YearInputUiStateTest {

    @Test
    fun `year input should be a list of every year for the next 15 years`() {
        val expectedYears = listOf("2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034")
        val calendar = GregorianCalendar(2020, 6, 1)
        val yearInputState = YearInputUiState.create(calendar)

        assertEquals(expectedYears, yearInputState.years)
    }
}
