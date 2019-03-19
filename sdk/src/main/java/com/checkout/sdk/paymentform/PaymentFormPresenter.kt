package com.checkout.sdk.paymentform

import com.checkout.sdk.architecture.BasePresenter
import com.checkout.sdk.architecture.MvpView


class PaymentFormPresenter(initialState: PaymentFormUiState = PaymentFormUiState()) :
    BasePresenter<MvpView<PaymentFormUiState>, PaymentFormUiState>(initialState) {

    fun getToken(getTokenUseCase: GetTokenUseCase) {
        getTokenUseCase.execute()
    }
}
