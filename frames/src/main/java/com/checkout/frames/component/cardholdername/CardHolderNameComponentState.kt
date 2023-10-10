package com.checkout.frames.component.cardholdername

import com.checkout.frames.component.base.InputComponentState
import kotlinx.coroutines.flow.MutableStateFlow

internal data class CardHolderNameComponentState(
    val inputState: InputComponentState,
    val isCardHolderNameFieldValid: MutableStateFlow<Boolean> = MutableStateFlow(false),
) {
    val cardHolderName by lazy { inputState.inputFieldState.text }

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
}
