package com.checkout.sdk.yearinput

import com.checkout.sdk.date.CardDate
import com.checkout.sdk.date.Month
import com.checkout.sdk.date.Year
import com.checkout.sdk.store.InMemoryStore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class YearSelectedUseCaseTest {

    @Mock
    private lateinit var store: InMemoryStore

    private val initialCardDate = CardDate(Month.FEBRUARY, Year(2089))

    @Test
    fun `given year is selected then it should be written to the data store`() {
        val years = listOf("2022", "2023")
        val expectedPosition = 1

        val year = Year(Integer.parseInt(years[expectedPosition]))
        val expectedCardDate = CardDate(initialCardDate.month, year)
        given(store.cardDate).willReturn(initialCardDate)

        val useCase = YearSelectedUseCase.Builder(store, expectedPosition)
            .years(years)
            .build()

        useCase.execute()

        then(store).should().cardDate = expectedCardDate
    }
}
