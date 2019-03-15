package com.checkout.sdk.carddetails

import com.checkout.sdk.architecture.UiState
import com.checkout.sdk.core.RequestValidity


data class CardDetailsUiState(
    val hideKeyboard: Boolean = true,
    val requestValidity: RequestValidity? = null,
    val inProgress: Boolean = false
) : UiState
