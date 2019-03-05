package com.checkout.android_sdk.UseCase

import com.checkout.android_sdk.Store.DataStore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class CvvFocusChangedUseCaseTest {

    @Mock
    private lateinit var dataStoreMock: DataStore

    @Mock
    private lateinit var callbackMock: CvvFocusChangedUseCase.Callback

    @Test
    fun `given cvv has length 3 and expected length is 4 then an error will be shown`() {
        val (cvv, hasFocus) = Pair("234", false)
        given(dataStoreMock.cvvLength).willReturn(4)
        CvvFocusChangedUseCase(cvv, hasFocus, dataStoreMock, callbackMock).execute()

        then(callbackMock).should().onFocusUpdated(true)
    }

    @Test
    fun `given cvv gains focus then error will be cleared`() {
        val (cvv, hasFocus) = Pair("23456", true)
        CvvFocusChangedUseCase(cvv, hasFocus, dataStoreMock, callbackMock).execute()

        then(callbackMock).should().onFocusUpdated(false)
    }
}
