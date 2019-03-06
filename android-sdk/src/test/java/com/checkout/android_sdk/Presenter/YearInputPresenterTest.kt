package com.checkout.android_sdk.Presenter

import com.checkout.android_sdk.Store.DataStore
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class YearInputPresenterTest {

    @Mock
    private lateinit var dataStore: DataStore

    @Mock
    private lateinit var viewMock: YearInputPresenter.YearInputView

    private lateinit var presenter: YearInputPresenter

    private lateinit var initialState: YearInputPresenter.YearInputUiState

    @Before
    fun onSetup() {
        initialState =
                YearInputPresenter.YearInputUiState(listOf("2080", "2081", "2082", "2083", "2084"), 3)
        presenter = YearInputPresenter(dataStore, initialState)
    }

    @Test
    fun `given year selected then view should be updated with selected year`() {
        val newPosition = 4
        val expectedState = initialState.copy(position = newPosition)
        presenter.start(viewMock)
        presenter.yearSelected(newPosition)

        then(viewMock).should().onStateUpdated(expectedState)
    }
}
