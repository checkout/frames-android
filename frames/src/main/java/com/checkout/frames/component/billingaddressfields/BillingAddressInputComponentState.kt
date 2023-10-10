package com.checkout.frames.component.billingaddressfields

import com.checkout.frames.component.base.InputComponentState
import kotlinx.coroutines.flow.MutableStateFlow

internal data class BillingAddressInputComponentState(
    val addressFieldName: String,
    var inputComponentState: InputComponentState,
    val isAddressFieldValid: MutableStateFlow<Boolean> = MutableStateFlow(false),
) {

    var isInputFieldOptional = inputComponentState.isInputFieldOptional

    val addressFieldText = inputComponentState.inputFieldState.text

    fun hideError() = switchErrorVisibility(false)

    fun showError(errorMessageId: Int) = with(inputComponentState.errorState) {
        textId.value = errorMessageId
        switchErrorVisibility(true)
    }

    fun showError(errorMessage: String) = with(inputComponentState.errorState) {
        text.value = errorMessage
        textId.value = null
        switchErrorVisibility(true)
    }

    private fun switchErrorVisibility(isVisible: Boolean) {
        inputComponentState.inputFieldState.isError.value = isVisible
        inputComponentState.errorState.isVisible.value = isVisible
    }
}
