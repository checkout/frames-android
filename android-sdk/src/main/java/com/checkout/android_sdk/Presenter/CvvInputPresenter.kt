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
    BasePresenter<CvvInputPresenter.CvvInputView, CvvInputPresenter.CvvInputUiState>(initialState),
    CvvInputUseCase.Callback, CvvFocusChangedUseCase.Callback {

    fun inputStateChanged(cvv: String) {
        CvvInputUseCase(dataStore, cvv, this).execute()
    }

    fun focusChanged(hasFocus: Boolean) {
        CvvFocusChangedUseCase(uiState.cvv, hasFocus, this).execute()
    }

    override fun onCvvUpdated(cvv: String, showError: Boolean) {
        val newState = CvvInputUiState(cvv, showError)
        safeUpdateView(newState)
    }

    override fun onFocusUpdated(showError: Boolean) {
        val newState = uiState.copy(showError = showError)
        safeUpdateView(newState)
    }

    data class CvvInputUiState(val cvv: String, val showError: Boolean) : UiState

    interface CvvInputView : MvpView<CvvInputUiState>

}
