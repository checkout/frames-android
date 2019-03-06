package com.checkout.sdk.presenter

import com.checkout.sdk.architecture.BasePresenter
import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.architecture.UiState
import com.checkout.sdk.store.DataStore
import com.checkout.sdk.usecase.CvvFocusChangedUseCase
import com.checkout.sdk.usecase.CvvInputUseCase


class CvvInputPresenter(
    private val dataStore: DataStore,
    initialState: CvvInputUiState = CvvInputUiState("", false)
) :
    BasePresenter<CvvInputPresenter.CvvInputView, CvvInputPresenter.CvvInputUiState>(initialState) {

    fun inputStateChanged(cvv: String) {
        val showError = CvvInputUseCase(dataStore, cvv).execute()
        val newState = uiState.copy(cvv = cvv, showError = showError)
        safeUpdateView(newState)
    }

    fun focusChanged(hasFocus: Boolean) {
        val showError = CvvFocusChangedUseCase(uiState.cvv, hasFocus, dataStore).execute()
        val newState = uiState.copy(showError = showError)
        safeUpdateView(newState)
    }

    data class CvvInputUiState(val cvv: String, val showError: Boolean) : UiState

    interface CvvInputView : MvpView<CvvInputUiState>

}
