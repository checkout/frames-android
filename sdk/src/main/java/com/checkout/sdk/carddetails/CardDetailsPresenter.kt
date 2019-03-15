package com.checkout.sdk.carddetails

import com.checkout.sdk.architecture.BasePresenter
import com.checkout.sdk.core.RequestValidity


class CardDetailsPresenter(initialState: CardDetailsUiState = CardDetailsUiState()) :
    BasePresenter<CardDetailsView, CardDetailsUiState>(initialState),
    PayButtonClickedUseCase.Callback {

    fun payButtonClicked(payButtonClickedUseCaseBuilder: PayButtonClickedUseCase.Builder) {
        uiState = CardDetailsUiState(inProgress = true)
        safeUpdateView(uiState)
        val payButtonClickedUseCase = payButtonClickedUseCaseBuilder
            .callback(this)
            .build()
        payButtonClickedUseCase.execute()
    }

    override fun onRequestValidity(requestValidity: RequestValidity) {
        uiState = CardDetailsUiState(requestValidity = requestValidity, inProgress = false)
        safeUpdateView(uiState)
    }
}
