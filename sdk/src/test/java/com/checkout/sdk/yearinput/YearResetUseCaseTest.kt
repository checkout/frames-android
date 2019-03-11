package com.checkout.sdk.yearinput

import com.checkout.sdk.store.InMemoryStore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class YearResetUseCaseTest {

    @Mock
    private lateinit var store: InMemoryStore

    @Test
    fun `when use case is executed then card month is written to the store`() {
        YearResetUseCase(store).execute()

        then(store).should().cardYear = Year(Year.UNKNOWN)
    }
}
