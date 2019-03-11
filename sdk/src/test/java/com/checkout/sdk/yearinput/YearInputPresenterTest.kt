package com.checkout.sdk.yearinput

import com.checkout.sdk.architecture.MvpView
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.Mockito.reset
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class YearInputPresenterTest {

    @Mock
    private lateinit var viewMock: MvpView<YearInputUiState>

    @Mock
    private lateinit var yearSelectedUseCaseBuilder: YearSelectedUseCase.Builder

    @Mock
    private lateinit var yearSelectedUseCase: YearSelectedUseCase

    @Mock
    private lateinit var yearResetUseCase: YearResetUseCase

    private lateinit var presenter: YearInputPresenter

    private lateinit var initialState: YearInputUiState

    @Before
    fun onSetup() {
        initialState = YearInputUiState(listOf("2080", "2081", "2082", "2083", "2084"), 3)
        presenter = YearInputPresenter(initialState)
        presenter.start(viewMock)
        reset(viewMock)
    }

    @Test
    fun `given year selected then view should be updated with selected year`() {
        val newPosition = 4
        val expectedState = initialState.copy(position = newPosition)
        given(yearSelectedUseCaseBuilder.years(initialState.years)).willReturn(yearSelectedUseCaseBuilder)
        given(yearSelectedUseCaseBuilder.position).willReturn(newPosition)
        given(yearSelectedUseCaseBuilder.build()).willReturn(yearSelectedUseCase)

        presenter.yearSelected(yearSelectedUseCaseBuilder)

        then(yearSelectedUseCase).should().execute()
        then(viewMock).should().onStateUpdated(expectedState)
    }

    @Test
    fun `given year reset then year reset use case should be executed and the presenter should reset the view state`() {
        presenter.reset(yearResetUseCase)

        then(yearResetUseCase).should().execute()
        then(viewMock).should().onStateUpdated(initialState.copy(position = 0))
    }

}
