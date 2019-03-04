package com.checkout.android_sdk.Presenter

import com.checkout.android_sdk.Store.DataStore
import com.checkout.android_sdk.Utils.DateFormatter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
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
    fun `given new months generated then view should be updated with values for months`() {
        val expectedMonths = getExpectedMonths()

        presenter.start(viewMock)
        reset(viewMock)
        presenter.onMonthsGenerated(expectedMonths.toTypedArray())

        then(viewMock).should()
            .onStateUpdated(MonthInputPresenter.MonthInputUiState(months = expectedMonths))
    }

    @Test
    fun `given month selected then view should be updated with selected month`() {
        val expectedMonths = getExpectedMonths()
        val (position, numberString, finished) = Triple(6, "06", true)
        presenter.start(viewMock)
        reset(viewMock)
        presenter.onMonthSelected(position, numberString, finished)

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
