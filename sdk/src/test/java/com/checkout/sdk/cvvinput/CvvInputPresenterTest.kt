package com.checkout.sdk.cvvinput

import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.store.DataStore
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class CvvInputPresenterTest {

    @Mock
    private lateinit var dataStore: DataStore

    @Mock
    private lateinit var viewMock: MvpView<CvvInputUiState>

    @Mock
    private lateinit var cvvInputUseCase: CvvInputUseCase

    @Mock
    private lateinit var cvvFocusChangedUseCase: CvvFocusChangedUseCase

    private lateinit var presenter: CvvInputPresenter

    private lateinit var initialState: CvvInputUiState

    @Before
    fun onSetup() {
        initialState = CvvInputUiState("12", false)
        presenter = CvvInputPresenter(initialState)
        presenter.start(viewMock)
    }

    @Test
    fun `given cvv updated show the new value`() {
        val expectedState = CvvInputUiState("34", false)
        given(cvvInputUseCase.getCvv()).willReturn(expectedState.cvv)

        presenter.inputStateChanged(cvvInputUseCase)

        then(viewMock).should().onStateUpdated(expectedState)
    }

    @Test
    fun `given cvv focus changed and it has an error then error should be shown`() {
        val expectedError = true
        given(cvvFocusChangedUseCase.execute()).willReturn(expectedError)

        presenter.focusChanged(cvvFocusChangedUseCase)

        then(viewMock).should().onStateUpdated(initialState.copy(showError = expectedError))
    }

}
