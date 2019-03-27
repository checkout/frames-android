package com.checkout.sdk.carddetails

import com.checkout.sdk.architecture.BasePresenter


class CardDetailsPresenter(initialState: CardDetailsUiState = CardDetailsUiState()) :
    BasePresenter<CardDetailsView, CardDetailsUiState>(initialState) {

    fun payButtonClicked(payButtonClickedUseCase: PayButtonClickedUseCase) {
        val cardDetailsValidity = payButtonClickedUseCase.execute()
        val newState = uiState.copy(cardDetailsValidity = cardDetailsValidity)
        safeUpdateView(newState)
    }

    fun showProgress(inProgress: Boolean) {
        val newState = CardDetailsUiState(inProgress = inProgress)
        safeUpdateView(newState)
    }

    fun initializeAcceptedCards(initializeAcceptedCardsUseCase: InitializeAcceptedCardsUseCase) {
        if (uiState.acceptedCards == null) {
            val acceptedCards = initializeAcceptedCardsUseCase.execute()
            val newState = uiState.copy(acceptedCards = acceptedCards)
            safeUpdateView(newState)
        }
    }

    fun updateBillingSpinner(updateBillingSpinnerUseCase: UpdateBillingSpinnerUseCase) {
        val spinnerStrings = updateBillingSpinnerUseCase.execute()
        spinnerStrings?.let {
            val newState = uiState.copy(spinnerStrings = spinnerStrings)
            safeUpdateView(newState)
        }
    }
}
