package com.checkout.frames.component.cvv

import com.checkout.frames.component.base.InputComponentState

internal data class CvvComponentState(
    val inputState: InputComponentState,
) {

    val cvv = inputState.inputFieldState.text
    val cvvLength = inputState.inputFieldState.maxLength

    fun hideError() = switchErrorVisibility(false)

    fun showError(errorMessageId: Int) = with(inputState.errorState) {
        textId.value = errorMessageId
        switchErrorVisibility(true)
    }

    private fun switchErrorVisibility(isVisible: Boolean) {
        inputState.inputFieldState.isError.value = isVisible
        inputState.errorState.isVisible.value = isVisible
    }
}
