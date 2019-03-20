package com.checkout.sdk.carddetails

import com.checkout.sdk.architecture.UiState
import com.checkout.sdk.core.CardDetailsValidity


data class CardDetailsUiState(
    val hideKeyboard: Boolean = true,
    val cardDetailsValidity: CardDetailsValidity? = null,
    val inProgress: Boolean = false
) : UiState
