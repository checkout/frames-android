package com.checkout.android_sdk.UseCase

import com.checkout.android_sdk.Store.DataStore
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class CvvInputUseCaseTest {

    @Mock
    private lateinit var dataStoreMock: DataStore

    @Test
    fun `given cvv updated then it should be written to data store and called back`() {
        val (cvv, expectedError) = Pair("234", false)
        val showError = CvvInputUseCase(dataStoreMock, cvv).execute()

        then(dataStoreMock).should().cardCvv = cvv
        assertEquals(expectedError, showError)
    }
}
