package com.checkout.sdk.paymentform

import com.checkout.sdk.architecture.UiState


data class PaymentFormUiState(
    val inProgress: Boolean? = null,
    val showing: Showing = Showing.CARD_DETAILS_SCREEN
) : UiState {
    enum class Showing { CARD_DETAILS_SCREEN, BILLING_DETAILS_SCREEN }
}
