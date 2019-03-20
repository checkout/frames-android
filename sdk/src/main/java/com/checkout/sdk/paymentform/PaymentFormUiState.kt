package com.checkout.sdk.paymentform

import com.checkout.sdk.architecture.UiState


data class PaymentFormUiState(val inProgress: Boolean = false) : UiState
