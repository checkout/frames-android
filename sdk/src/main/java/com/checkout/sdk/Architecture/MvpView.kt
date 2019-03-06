package com.checkout.sdk.Architecture


interface MvpView<in U: UiState> {
    fun onStateUpdated(uiState: U)
}
