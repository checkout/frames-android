package com.checkout.sdk.architecture

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
class BasePresenterTest {

    @Mock
    private lateinit var uiState: UiState

    @Mock
    private lateinit var viewMock: MvpView<UiState>

    private lateinit var presenter: BasePresenter<MvpView<UiState>, UiState>

    @BeforeEach
    fun setup() {
        presenter = object : BasePresenter<MvpView<UiState>, UiState>(uiState) { }
    }

    @Test
    fun `given presenter is started then default state should be set`() {
        presenter.start(viewMock)

        then(viewMock).should().onStateUpdated(uiState)
    }

    @Test
    fun `given presenter is stopped then text changes then no view update should happen`() {
        presenter.start(viewMock)
        presenter.stop()
        reset(viewMock)

        presenter.safeUpdateView(uiState)

        then(viewMock).shouldHaveNoMoreInteractions()
    }

    @Test
    fun `given presenter is stopped then restarted then ui state should be set again`() {
        presenter.start(viewMock)
        presenter.stop()
        reset(viewMock)

        presenter.start(viewMock)

        then(viewMock).should().onStateUpdated(uiState)
    }

    @Test
    fun `given ui state is updated after the presenter is stopped then it should be applied when the presenter starts again`() {
        presenter.start(viewMock)
        presenter.stop()
        reset(viewMock)

        val newUiState = mock(UiState::class.java)
        presenter.safeUpdateView(newUiState)
        then(viewMock).shouldHaveZeroInteractions()

        presenter.start(viewMock)

        then(viewMock).should().onStateUpdated(newUiState)
    }
}
