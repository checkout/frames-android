package com.checkout.sdk.cvvinput

import com.checkout.sdk.architecture.UiState


data class TextInputUiState(val text: String = "", val showError: Boolean = false) : UiState
