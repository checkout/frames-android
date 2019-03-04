package com.checkout.android_sdk.UseCase

import com.checkout.android_sdk.Store.DataStore
import com.checkout.android_sdk.Utils.DateFormatter
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MonthSelectedUseCaseTest {

    @Mock
    private lateinit var dateFormatter: DateFormatter

    @Mock
    private lateinit var dataStoreMock: DataStore

    @Mock
    private lateinit var callbackMock: MonthSelectedUseCase.Callback

    @Test
    fun `given valid card without focus then card focus result is error`() {
        val (monthSelectedPosition, formattedPosition, finished) = Triple(6, "06", true)
        given(dateFormatter.formatMonth(monthSelectedPosition + 1)).willReturn(formattedPosition)
        MonthSelectedUseCase(dateFormatter, monthSelectedPosition, dataStoreMock, callbackMock).execute()

        then(callbackMock).should().onMonthSelected(monthSelectedPosition, formattedPosition, finished)
        then(dataStoreMock).should().cardMonth = formattedPosition
    }
}
