package com.checkout.frames.component.expirydate

import com.checkout.frames.component.base.InputComponentState

internal data class ExpiryDateComponentState(
    val inputState: InputComponentState
) {
    val expiryDate = inputState.inputFieldState.text

    fun hideError() = switchErrorVisibility(false)

    fun showError(errorMessageId: Int) = with(inputState.errorState) {
        textId.value = errorMessageId
        switchErrorVisibility(true)
    }

    fun showError(errorMessage: String) = with(inputState.errorState) {
        text.value = errorMessage
        textId.value = null
        switchErrorVisibility(true)
    }

    private fun switchErrorVisibility(isVisible: Boolean) {
        inputState.inputFieldState.isError.value = isVisible
        inputState.errorState.isVisible.value = isVisible
    }
    val expiryDateMaxLength = inputState.inputFieldState.maxLength
}
