package com.checkout.sdk.cvvinput

import com.checkout.sdk.architecture.UiState


data class CvvInputUiState(val cvv: String = "", val showError: Boolean = false) : UiState
