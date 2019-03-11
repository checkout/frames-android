package com.checkout.sdk.monthinput

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
class MonthResetUseCaseTest {

    @Mock
    private lateinit var store: InMemoryStore

    private val initialCardDate = CardDate(Month.AUGUST, Year(2020))

    @Test
    fun `when use case is executed then card month is written to the store`() {
        val expectedCardDate = initialCardDate.copy(month = Month.UNKNOWN)
        given(store.cardDate).willReturn(initialCardDate)

        MonthResetUseCase(store).execute()

        then(store).should().cardDate = expectedCardDate
    }
}
