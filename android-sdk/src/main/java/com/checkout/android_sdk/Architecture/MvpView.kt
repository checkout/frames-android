package com.checkout.android_sdk.Architecture


interface MvpView<in U: UiState> {
    fun onStateUpdated(uiState: U)
}
