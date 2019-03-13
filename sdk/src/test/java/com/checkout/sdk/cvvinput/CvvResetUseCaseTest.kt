package com.checkout.sdk.cvvinput

import com.checkout.sdk.store.InMemoryStore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class CvvResetUseCaseTest {

    @Mock
    private lateinit var store: InMemoryStore

    @Test
    fun `given cvv is reset then store should have cvv value reset`() {
        CvvResetUseCase(store).execute()

        then(store).should().cvv = Cvv.UNKNOWN
    }
}
