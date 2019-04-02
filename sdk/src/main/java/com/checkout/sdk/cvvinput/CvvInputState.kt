package com.checkout.sdk.cvvinput

import com.checkout.sdk.architecture.UiState


data class TextInputUiState(val cvv: String = "", val showError: Boolean = false) : UiState
