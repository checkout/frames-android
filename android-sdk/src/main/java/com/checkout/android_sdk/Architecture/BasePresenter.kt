package com.checkout.android_sdk.Architecture

/**
 * BasePresenter is responsible for
 * - starting and stopping of the presenter
 * - keeping track of ui state
 * - safely updating the view
 */
abstract class BasePresenter<in V : MvpView<U>, U: UiState> (protected var uiState: U) {

    private var view: V? = null

    fun start(view: V) {
        this.view = view
        safeUpdateView(uiState)
    }

    fun stop() {
        view = null
    }

    fun safeUpdateView(uiState: U) {
        this.uiState = uiState
        view?.onStateUpdated(uiState)
    }
}
