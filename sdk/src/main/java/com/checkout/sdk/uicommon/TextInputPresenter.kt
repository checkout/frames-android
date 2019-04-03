package com.checkout.sdk.uicommon

import com.checkout.sdk.architecture.BasePresenter
import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.cvvinput.CvvResetUseCase


class TextInputPresenter(initialState: TextInputUiState = TextInputUiState()) :
    BasePresenter<MvpView<TextInputUiState>, TextInputUiState>(initialState) {

    fun inputStateChanged(textInputUseCase: TextInputUseCase) {
        textInputUseCase.execute()
        val newState = uiState.copy(text = textInputUseCase.text, showError = false)
        safeUpdateView(newState)
    }

    fun focusChanged(focusChangedUseCase: TextInputFocusChangedUseCase) {
        val showError = focusChangedUseCase.execute()
        val newState = uiState.copy(showError = showError)
        safeUpdateView(newState)
    }

    fun reset(cvvResetUseCase: CvvResetUseCase) {
        cvvResetUseCase.execute()
        val newState = TextInputUiState()
        safeUpdateView(newState)
    }

    fun showError(show: Boolean) {
        val newState = uiState.copy(showError = show)
        safeUpdateView(newState)
    }
}
