package com.checkout.android_sdk.UseCase

import com.checkout.android_sdk.Store.DataStore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class CvvInputUseCaseTest {

    @Mock
    private lateinit var dataStoreMock: DataStore

    @Mock
    private lateinit var callbackMock: CvvInputUseCase.Callback

    @Test
    fun `given cvv updated then it should be written to data store and called back`() {
        val (cvv, expectedError) = Pair("234", false)
        CvvInputUseCase(dataStoreMock, cvv, callbackMock).execute()

        then(dataStoreMock).should().cardCvv = cvv
        then(callbackMock).should().onCvvUpdated(cvv, expectedError)
    }
}
