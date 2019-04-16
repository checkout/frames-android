package com.checkout.sdk.billingdetails

import com.checkout.sdk.architecture.BasePresenter
import com.checkout.sdk.architecture.MvpView


class BillingDetailsPresenter(uiState: BillingDetailsUiState = BillingDetailsUiState()) :
    BasePresenter<MvpView<BillingDetailsUiState>, BillingDetailsUiState>(uiState) {

    fun countrySelected(countrySelectedUseCaseBuilder: CountrySelectedUseCase.Builder) {
        countrySelectedUseCaseBuilder.countries(uiState.countries)
            .build()
            .execute()
        val newState = uiState.copy(position = countrySelectedUseCaseBuilder.position)
        safeUpdateView(newState)
    }

    fun doneButtonClicked(doneButtonClickedUseCase: DoneButtonClickedUseCase) {
        val validity = doneButtonClickedUseCase.execute()
        val newState = uiState.copy(billingDetailsValidity = validity)
        safeUpdateView(newState)
    }
}
