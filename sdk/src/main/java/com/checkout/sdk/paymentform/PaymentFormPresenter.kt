package com.checkout.sdk.paymentform

import com.checkout.sdk.architecture.BasePresenter
import com.checkout.sdk.architecture.MvpView


class PaymentFormPresenter(initialState: PaymentFormUiState = PaymentFormUiState()) :
    BasePresenter<MvpView<PaymentFormUiState>, PaymentFormUiState>(initialState),
    GetTokenUseCase.Callback {

    fun getToken(getTokenUseCaseBuilder: GetTokenUseCase.Builder) {
        getTokenUseCaseBuilder
            .callback(this)
            .build()
            .execute()
    }

    override fun onProgressChanged(inProgress: Boolean) {
        val newUiState = uiState.copy(inProgress = inProgress)
        safeUpdateView(newUiState)
    }
}
