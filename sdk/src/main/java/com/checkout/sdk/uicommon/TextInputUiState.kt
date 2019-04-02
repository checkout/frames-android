package com.checkout.sdk.uicommon

import com.checkout.sdk.architecture.UiState


data class TextInputUiState(val text: String = "", val showError: Boolean = false) : UiState
