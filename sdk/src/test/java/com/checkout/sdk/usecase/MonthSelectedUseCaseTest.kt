package com.checkout.sdk.usecase

import com.checkout.sdk.store.DataStore
import com.checkout.sdk.utils.DateFormatter
import junit.framework.Assert.assertEquals
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

    @Test
    fun `given valid card without focus then card focus result is error`() {
        val expectedResult = MonthSelectedUseCase.MonthSelectedResult(6, "06", true)
        given(dateFormatter.formatMonth(expectedResult.position + 1)).willReturn(expectedResult.numberString)
        val monthSelectedResult = MonthSelectedUseCase(dateFormatter, expectedResult.position, dataStoreMock).execute()

        assertEquals(expectedResult, monthSelectedResult)
        then(dataStoreMock).should().cardMonth = expectedResult.numberString
    }
}
