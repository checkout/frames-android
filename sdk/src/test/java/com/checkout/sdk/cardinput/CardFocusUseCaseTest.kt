package com.checkout.sdk.cardinput

import com.checkout.sdk.store.DataStore
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CardFocusUseCaseTest {

    @Mock
    private lateinit var dataStore: DataStore

    @Test
    fun `given valid card without focus then card focus result is error`() {
        given(dataStore.cardNumber).willReturn("1234567812345678")

        val showError = CardFocusUseCase(false, dataStore).execute()

        assertEquals(showError, true)
    }

    @Test
    fun `given valid card field has focus then card focus result is no error`() {
        val showError = CardFocusUseCase(true, dataStore).execute()

        assertEquals(showError, false)
    }

    @Test
    fun `given invalid card without focus then card focus result is error`() {
        given(dataStore.cardNumber).willReturn("11")

        val showError = CardFocusUseCase(false, dataStore).execute()

        assertEquals(showError, true)
    }

}
