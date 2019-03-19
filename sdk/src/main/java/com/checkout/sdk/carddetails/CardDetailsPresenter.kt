package com.checkout.sdk.carddetails

import com.checkout.sdk.architecture.BasePresenter


class CardDetailsPresenter(initialState: CardDetailsUiState = CardDetailsUiState()) :
    BasePresenter<CardDetailsView, CardDetailsUiState>(initialState) {

    fun payButtonClicked(payButtonClickedUseCase: PayButtonClickedUseCase) {
        val cardDetailsValidity = payButtonClickedUseCase.execute()
        uiState = CardDetailsUiState(cardDetailsValidity = cardDetailsValidity)
        safeUpdateView(uiState)
    }
}
