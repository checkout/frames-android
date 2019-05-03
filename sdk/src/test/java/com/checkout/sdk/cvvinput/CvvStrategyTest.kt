package com.checkout.sdk.cvvinput

import com.checkout.sdk.store.InMemoryStore
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
class CvvStrategyTest {

    @Mock
    private lateinit var storeMock: InMemoryStore

    @Test
    fun `given cvv has length 5 and expected length is 2 then an error will be shown`() {
        var initialCvv = Cvv("345678", 2)
        given(storeMock.cvv).willReturn(initialCvv)
        val showError = CvvStrategy(storeMock)
            .focusChanged(false)

        assertTrue(showError)
    }

    @Test
    fun `given cvv has length 3 and expected length is 4 then an error will be shown`() {
        val initialCvv = Cvv("146", 4)
        given(storeMock.cvv).willReturn(initialCvv)
        val showError = CvvStrategy(storeMock)
            .focusChanged(false)

        assertTrue(showError)
    }

    @Test
    fun `given cvv gains focus then error will be cleared`() {
        val hasFocus = true
        val showError = CvvStrategy(storeMock)
            .focusChanged(hasFocus)

        assertEquals(false, showError)
    }

    @Test
    fun `given cvv updated then it should be written to data store and called back`() {
        val initialCvv = Cvv("345678", 2)
        val newCvv = "123"
        given(storeMock.cvv).willReturn(initialCvv)

        CvvStrategy(storeMock).textChanged(newCvv)

        then(storeMock).should().cvv = Cvv(newCvv, initialCvv.expectedLength)
    }

    @Test
    fun `given cvv is reset then store should have cvv value reset`() {
        CvvStrategy(storeMock).reset()

        then(storeMock).should().cvv = Cvv.UNKNOWN
    }
}
