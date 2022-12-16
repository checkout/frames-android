package com.checkout.frames.component.cardnumber

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.checkout.base.model.CardScheme
import com.checkout.frames.component.base.InputComponentState

internal data class CardNumberComponentState(
    val inputState: InputComponentState,
    val cardScheme: MutableState<CardScheme> = mutableStateOf(CardScheme.UNKNOWN)
) {
    val cardNumber = inputState.inputFieldState.text
    val cardNumberLength = inputState.inputFieldState.maxLength

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
