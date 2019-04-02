package com.checkout.sdk.cvvinput

import com.checkout.sdk.architecture.BasePresenter
import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.core.TextInputStrategy


class TextInputPresenter (
    private val strategy: TextInputStrategy,
    initialState: TextInputUiState = TextInputUiState()
) : BasePresenter<MvpView<TextInputUiState>, TextInputUiState>(initialState) {

    fun inputStateChanged(textInputUseCaseBuilder: TextInputUseCase.Builder) {
        textInputUseCaseBuilder.strategy(strategy).build().execute()
        val newState = uiState.copy(cvv = textInputUseCaseBuilder.text, showError = false)
        safeUpdateView(newState)
    }

    fun focusChanged(cvvFocusChangedUseCase: CvvFocusChangedUseCase) {
        val showError = cvvFocusChangedUseCase.execute()
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
