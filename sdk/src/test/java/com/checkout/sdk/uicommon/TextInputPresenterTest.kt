package com.checkout.sdk.uicommon

import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.uicommon.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class TextInputPresenterTest {

    @Mock
    private lateinit var viewMock: MvpView<TextInputUiState>

    @Mock
    private lateinit var textInputUseCase: TextInputUseCase

    @Mock
    private lateinit var cvvFocusChangedUseCase: TextInputFocusChangedUseCase

    @Mock
    private lateinit var textInputResetUseCase: TextInputResetUseCase

    private lateinit var presenter: TextInputPresenter

    private lateinit var initialState: TextInputUiState

    @Before
    fun onSetup() {
        initialState = TextInputUiState("12", false)
        presenter = TextInputPresenter(initialState)
        presenter.start(viewMock)
    }

    @Test
    fun `given cvv updated show the new value`() {
        val expectedState = TextInputUiState("34", false)
        given(textInputUseCase.text).willReturn(expectedState.text)

        presenter.inputStateChanged(textInputUseCase)

        then(viewMock).should().onStateUpdated(expectedState)
    }

    @Test
    fun `given cvv focus changed and it has an error then error should be shown`() {
        val expectedError = true
        given(cvvFocusChangedUseCase.execute()).willReturn(expectedError)

        presenter.focusChanged(cvvFocusChangedUseCase)

        then(viewMock).should().onStateUpdated(initialState.copy(showError = expectedError))
    }

    @Test
    fun `given presenter is started when reset is called then the use case is executed and the state is the initial state`() {
        presenter.reset(textInputResetUseCase)

        then(textInputResetUseCase).should().execute()
        then(viewMock).should().onStateUpdated(TextInputUiState())
    }

    @Test
    fun `given presenter is started when show error called then the state is the same except with showError being updated`() {
        val expectedError = true
        presenter.showError(expectedError)

        then(viewMock).should().onStateUpdated(initialState.copy(showError = expectedError))
    }
}
