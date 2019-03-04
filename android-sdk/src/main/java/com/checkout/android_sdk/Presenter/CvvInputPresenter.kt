package com.checkout.android_sdk.Presenter

import com.checkout.android_sdk.Architecture.BasePresenter
import com.checkout.android_sdk.Architecture.MvpView
import com.checkout.android_sdk.Architecture.UiState
import com.checkout.android_sdk.Store.DataStore
import com.checkout.android_sdk.UseCase.CvvInputUseCase


class CvvInputPresenter(private val dataStore: DataStore) :
    BasePresenter<CvvInputPresenter.CvvInputView, CvvInputPresenter.CvvInputUiState>(
        CvvInputUiState("")
    ), CvvInputUseCase.Callback {

    fun inputStateChanged(cvv: String) {
        CvvInputUseCase(dataStore, cvv, this).execute()
    }

    override fun onCvvUpdated(cvv: String) {
        val newState = uiState.copy(cvv = cvv)
        safeUpdateView(newState)
    }

    data class CvvInputUiState(val cvv: String) : UiState

    interface CvvInputView : MvpView<CvvInputUiState>

}
