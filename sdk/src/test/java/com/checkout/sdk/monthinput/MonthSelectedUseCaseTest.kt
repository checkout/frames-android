package com.checkout.sdk.monthinput

import com.checkout.sdk.date.CardDate
import com.checkout.sdk.date.Month
import com.checkout.sdk.date.Year
import com.checkout.sdk.store.InMemoryStore
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class MonthSelectedUseCaseTest {

    @Mock
    private lateinit var store: InMemoryStore

    private val initialCardDate = CardDate(Month.SEPTEMBER, Year(2044))

    @Test
    fun `when month is selected then position is written to store`() {
        val expectedCardDate = initialCardDate.copy(month = Month.JULY)
        given(store.cardDate).willReturn(initialCardDate)
        val position = 6

        MonthSelectedUseCase(position, store).execute()

        then(store).should().cardDate = expectedCardDate
    }
}
