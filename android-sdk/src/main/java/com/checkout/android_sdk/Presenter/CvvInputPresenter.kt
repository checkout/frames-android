package com.checkout.android_sdk.Presenter

import com.checkout.android_sdk.Architecture.BasePresenter
import com.checkout.android_sdk.Architecture.MvpView
import com.checkout.android_sdk.Architecture.UiState
import com.checkout.android_sdk.Store.DataStore


class CvvInputPresenter(private val dataStore: DataStore) :
    BasePresenter<CvvInputPresenter.CvvInputView, CvvInputPresenter.CvvInputUiState>(
        CvvInputUiState("")
    ) {

    data class CvvInputUiState(private val string: String) : UiState

    interface CvvInputView : MvpView<CvvInputUiState>

}
