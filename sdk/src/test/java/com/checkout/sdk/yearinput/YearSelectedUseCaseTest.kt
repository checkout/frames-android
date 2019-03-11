package com.checkout.sdk.yearinput

import com.checkout.sdk.store.InMemoryStore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class YearSelectedUseCaseTest {

    @Mock
    private lateinit var store: InMemoryStore

    @Test
    fun `given year is selected then it should be written to the data store`() {
        val years = listOf("2022", "2023")
        val expectedPosition = 1

        val useCase = YearSelectedUseCase.Builder(store, expectedPosition)
            .years(years)
            .build()

        useCase.execute()

        then(store).should().cardYear = Year(Integer.parseInt(years[expectedPosition]))
    }
}
