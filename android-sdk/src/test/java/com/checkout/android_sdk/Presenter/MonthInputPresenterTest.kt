package com.checkout.android_sdk.Presenter

import com.checkout.android_sdk.Store.DataStore
import com.checkout.android_sdk.Utils.DateFormatter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.Mockito.reset
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MonthInputPresenterTest {

    @Mock
    private lateinit var dateFormatter: DateFormatter

    @Mock
    private lateinit var dataStore: DataStore

    @Mock
    private lateinit var viewMock: MonthInputPresenter.MonthInputView

    private lateinit var presenter: MonthInputPresenter

    @Before
    fun onSetup() {
        presenter = MonthInputPresenter(dateFormatter, dataStore)
    }

    @Test
    fun `given month selected then view should be updated with selected month`() {
        val expectedMonths = getExpectedMonths()
        val (position, numberString, finished) = Triple(6, "06", true)
        given(dateFormatter.formatMonth(position + 1)).willReturn(numberString)
        presenter.start(viewMock)
        reset(viewMock)
        presenter.monthSelected(position)

        then(viewMock).should().onStateUpdated(
            MonthInputPresenter.MonthInputUiState(
                expectedMonths,
                position = position,
                numberString = numberString,
                finished = finished
            )
        )
    }

    private fun getExpectedMonths(): List<String> {
        return listOf(
            "JAN - null",
            "FEB - null",
            "MAR - null",
            "APR - null",
            "MAY - null",
            "JUN - null",
            "JUL - null",
            "AUG - null",
            "SEP - null",
            "OCT - null",
            "NOV - null",
            "DEC - null",
            " - null" // JVM adds this additional entry not present on Android OS
        )
    }
}
