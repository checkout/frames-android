package com.checkout.sdk.cvvinput

import com.checkout.sdk.architecture.BasePresenter
import com.checkout.sdk.architecture.MvpView


class CvvInputPresenter(initialState: CvvInputUiState = CvvInputUiState()
) : BasePresenter<MvpView<CvvInputUiState>, CvvInputUiState>(initialState) {

    fun inputStateChanged(cvvInputUseCase: CvvInputUseCase) {
        cvvInputUseCase.execute()
        val newState = uiState.copy(cvv = cvvInputUseCase.cvv, showError = false)
        safeUpdateView(newState)
    }

    fun focusChanged(cvvFocusChangedUseCase: CvvFocusChangedUseCase) {
        val showError = cvvFocusChangedUseCase.execute()
        val newState = uiState.copy(showError = showError)
        safeUpdateView(newState)
    }

    fun reset(cvvResetUseCase: CvvResetUseCase) {
        cvvResetUseCase.execute()
        val newState = CvvInputUiState()
        safeUpdateView(newState)
    }

    fun showError(show: Boolean) {
        val newState = uiState.copy(showError = show)
        safeUpdateView(newState)
    }
}
