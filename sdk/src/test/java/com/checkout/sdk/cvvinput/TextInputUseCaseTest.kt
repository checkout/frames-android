package com.checkout.sdk.cvvinput

import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.uicommon.TextInputUseCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class TextInputUseCaseTest {

    @Mock
    private lateinit var store: InMemoryStore

    private val initialCvv = Cvv("112", 3)

    @Test
    fun `given cvv updated then it should be written to data store and called back`() {
        val cvv = "234"
        given(store.cvv).willReturn(initialCvv)

        TextInputUseCase(store, cvv).execute()

        then(store).should().cvv = Cvv(cvv, initialCvv.expectedLength)
    }
}
