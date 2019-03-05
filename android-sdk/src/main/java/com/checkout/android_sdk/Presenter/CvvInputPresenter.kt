package com.checkout.android_sdk.Presenter

import com.checkout.android_sdk.Architecture.BasePresenter
import com.checkout.android_sdk.Architecture.MvpView
import com.checkout.android_sdk.Architecture.UiState
import com.checkout.android_sdk.Store.DataStore
import com.checkout.android_sdk.UseCase.CvvFocusChangedUseCase
import com.checkout.android_sdk.UseCase.CvvInputUseCase


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
