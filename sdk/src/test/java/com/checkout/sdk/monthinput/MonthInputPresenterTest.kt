package com.checkout.sdk.monthinput

import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.store.DataStore
import com.checkout.sdk.utils.DateFormatter
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
    private lateinit var viewMock: MvpView<MonthInputUiState>

    private lateinit var presenter: MonthInputPresenter

    private lateinit var initialState: MonthInputUiState

    @Before
    fun onSetup() {
        initialState = MonthInputUiState(getExpectedMonths())
        presenter = MonthInputPresenter(dateFormatter, initialState)
        presenter.start(viewMock)
        reset(viewMock)
    }

    @Test
    fun `given month selected then view should be updated with selected month`() {
        val (position, numberString, finished) = Triple(6, "06", true)
        given(dateFormatter.formatMonth(position + 1)).willReturn(numberString)

        presenter.monthSelected(MonthSelectedUseCase(dateFormatter, position, dataStore))

        then(viewMock).should().onStateUpdated(
            MonthInputUiState(
                getExpectedMonths(),
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
