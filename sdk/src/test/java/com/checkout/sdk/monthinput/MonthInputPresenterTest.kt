package com.checkout.sdk.monthinput

import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.utils.DateFormatter
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.Mockito.reset
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class MonthInputPresenterTest {

    @Mock
    private lateinit var dateFormatter: DateFormatter

    @Mock
    private lateinit var monthSelectedUseCase: MonthSelectedUseCase

    @Mock
    private lateinit var viewMock: MvpView<MonthInputUiState>

    @Mock
    private lateinit var monthResetUseCase: MonthResetUseCase

    private lateinit var presenter: MonthInputPresenter

    private lateinit var initialState: MonthInputUiState

    @BeforeEach
    fun onSetup() {
        initialState = MonthInputUiState(getExpectedMonths())
        presenter = MonthInputPresenter(dateFormatter, initialState)
        presenter.start(viewMock)
        reset(viewMock)
    }

    @Test
    fun `given month selected then view should be updated with selected month`() {
        val position = 6
        given(monthSelectedUseCase.monthSelectedPosition).willReturn(position)

        presenter.monthSelected(monthSelectedUseCase)

        then(viewMock).should().onStateUpdated(
            MonthInputUiState(getExpectedMonths(), position = position)
        )
    }

    @Test
    fun `given a started presenter when reset is called the view is updated with the default state`() {
        presenter.reset(monthResetUseCase)

        then(monthResetUseCase).should().execute()
        then(viewMock).should().onStateUpdated(MonthInputUiState())
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
