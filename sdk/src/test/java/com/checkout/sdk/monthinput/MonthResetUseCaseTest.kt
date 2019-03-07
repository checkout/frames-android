package com.checkout.sdk.monthinput

import com.checkout.sdk.store.InMemoryStore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MonthResetUseCaseTest {

    @Mock
    private lateinit var store: InMemoryStore

    @Test
    fun `when use case is executed then card month is written to the store`() {
        MonthResetUseCase(store).execute()

        then(store).should().cardMonth = null
    }
}
