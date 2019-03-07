package com.checkout.sdk.cvvinput

import com.checkout.sdk.architecture.BasePresenter
import com.checkout.sdk.architecture.MvpView


class CvvInputPresenter(initialState: CvvInputUiState = CvvInputUiState()
) : BasePresenter<MvpView<CvvInputUiState>, CvvInputUiState>(initialState) {

    fun inputStateChanged(cvvInputUseCase: CvvInputUseCase) {
        val showError = cvvInputUseCase.execute()
        val newState = uiState.copy(cvv = cvvInputUseCase.getCvv(), showError = showError)
        safeUpdateView(newState)
    }

    fun focusChanged(cvvFocusChangedUseCase: CvvFocusChangedUseCase) {
        val showError = cvvFocusChangedUseCase.execute()
        val newState = uiState.copy(showError = showError)
        safeUpdateView(newState)
    }
}
