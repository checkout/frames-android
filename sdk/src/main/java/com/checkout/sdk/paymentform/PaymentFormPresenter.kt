package com.checkout.sdk.paymentform

import com.checkout.sdk.architecture.BasePresenter
import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.core.RequestMaker


class PaymentFormPresenter(initialState: PaymentFormUiState = PaymentFormUiState()) :
    BasePresenter<MvpView<PaymentFormUiState>, PaymentFormUiState>(initialState),
    RequestMaker.Callback {

    fun getToken(getTokenUseCase: GetTokenUseCase) {
        getTokenUseCase.execute()
    }

    override fun onProgressChanged(inProgress: Boolean) {
        val newUiState = uiState.copy(inProgress = inProgress)
        safeUpdateView(newUiState)
    }

    fun changeScreen(screen: PaymentFormUiState.Showing) {
        val newState = uiState.copy(showing = screen)
        safeUpdateView(newState)
    }
}
