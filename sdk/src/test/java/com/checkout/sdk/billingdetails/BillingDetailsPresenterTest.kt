package com.checkout.sdk.billingdetails

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
class BillingDetailsPresenterTest {

    @Mock
    private lateinit var viewMock: MvpView<BillingDetailsUiState>

    @Mock
    private lateinit var countrySelectedUseCaseBuilder: CountrySelectedUseCase.Builder

    @Mock
    private lateinit var countrySelectedUseCase: CountrySelectedUseCase

    @Mock
    private lateinit var doneButtonClickedUseCase: DoneButtonClickedUseCase

    private lateinit var presenter: BillingDetailsPresenter

    private lateinit var initialState: BillingDetailsUiState

    @Before
    fun onSetup() {
        initialState = BillingDetailsUiState(
            listOf("Mexico", "Yemen", "New Zealand"),
            1,
            BillingDetailsValidity(false, false, false, false, false, false, false, false)
        )
        presenter = BillingDetailsPresenter(initialState)
        presenter.start(viewMock)
        reset(viewMock)
    }

    @Test
    fun `when country selected then use case executed and position updated`() {
        val expectedPosition = 2
        given(countrySelectedUseCaseBuilder.countries(initialState.countries)).willReturn(
            countrySelectedUseCaseBuilder
        )
        given(countrySelectedUseCaseBuilder.build()).willReturn(countrySelectedUseCase)
        given(countrySelectedUseCaseBuilder.position).willReturn(expectedPosition)

        presenter.countrySelected(countrySelectedUseCaseBuilder)

        then(countrySelectedUseCase).should().execute()
        then(viewMock).should().onStateUpdated(initialState.copy(position = expectedPosition))
    }

    @Test
    fun `when done button is clicked the use case is executed and the validity is updated`() {
        val expectedValidity =
            BillingDetailsValidity(false, true, false, true, true, false, false, true)
        given(doneButtonClickedUseCase.execute()).willReturn(expectedValidity)

        presenter.doneButtonClicked(doneButtonClickedUseCase)

        then(doneButtonClickedUseCase).should().execute()
        then(viewMock).should().onStateUpdated(initialState.copy(billingDetailsValidity = expectedValidity))
    }
}
