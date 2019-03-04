package com.checkout.android_sdk.UseCase

import com.checkout.android_sdk.Store.DataStore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class YearSelectedUseCaseTest {

    @Mock
    private lateinit var dataStoreMock: DataStore

    @Mock
    private lateinit var callbackMock: YearSelectedUseCase.Callback

    @Test
    fun `given year is selected then it should be communicated to the data store and called back`() {
        val years = listOf("2022", "2023")
        val expectedPosition = 1

        YearSelectedUseCase(dataStoreMock, years, expectedPosition, callbackMock).execute()

        then(callbackMock).should().onYearSelected(expectedPosition)
        then(dataStoreMock).should().cardYear = years[expectedPosition]
    }
}
