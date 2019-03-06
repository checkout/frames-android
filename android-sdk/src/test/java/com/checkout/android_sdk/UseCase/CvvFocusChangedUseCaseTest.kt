package com.checkout.android_sdk.UseCase

import com.checkout.android_sdk.Store.DataStore
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class CvvFocusChangedUseCaseTest {

    @Mock
    private lateinit var dataStoreMock: DataStore

    @Test
    fun `given cvv has length 3 and expected length is 4 then an error will be shown`() {
        val (cvv, hasFocus) = Pair("234", false)
        given(dataStoreMock.cvvLength).willReturn(4)
        val showError = CvvFocusChangedUseCase(cvv, hasFocus, dataStoreMock).execute()

        assertEquals(true, showError)
    }

    @Test
    fun `given cvv gains focus then error will be cleared`() {
        val (cvv, hasFocus) = Pair("23456", true)
        val showError = CvvFocusChangedUseCase(cvv, hasFocus, dataStoreMock).execute()

        assertEquals(false, showError)
    }
}
