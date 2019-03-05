package com.checkout.android_sdk.Presenter

import com.checkout.android_sdk.Store.DataStore
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class CvvInputPresenterTest {

    @Mock
    private lateinit var dataStore: DataStore

    @Mock
    private lateinit var viewMock: CvvInputPresenter.CvvInputView

    private lateinit var presenter: CvvInputPresenter

    private lateinit var initialState: CvvInputPresenter.CvvInputUiState

    @Before
    fun onSetup() {
        initialState = CvvInputPresenter.CvvInputUiState("12", false)
        presenter = CvvInputPresenter(dataStore, initialState)
        presenter.start(viewMock)
    }

    @Test
    fun `given cvv updated show the new value and an error`() {
        val expectedState = CvvInputPresenter.CvvInputUiState("34", true)
        presenter.onCvvUpdated(expectedState.cvv, expectedState.showError)

        then(viewMock).should().onStateUpdated(expectedState)
    }

    @Test
    fun `given cvv focus updated and it has an error then error should be shown`() {
        val expectedError = true
        presenter.onFocusUpdated(showError = expectedError)

        then(viewMock).should().onStateUpdated(initialState.copy(showError = expectedError))
    }

}
