package com.checkout.sdk.architecture


interface MvpView<in U: UiState> {
    fun onStateUpdated(uiState: U)
}
