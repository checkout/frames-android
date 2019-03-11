package com.checkout.sdk.monthinput

import com.checkout.sdk.store.InMemoryStore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MonthSelectedUseCaseTest {

    @Mock
    private lateinit var store: InMemoryStore

    @Test
    fun `when month is selected then position is written to store`() {
        val position = 6
        val expectedMonth = Month.JULY
        MonthSelectedUseCase(position, store).execute()

        then(store).should().cardMonth = expectedMonth
    }
}
