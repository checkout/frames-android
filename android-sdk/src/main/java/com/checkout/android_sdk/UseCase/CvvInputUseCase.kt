package com.checkout.android_sdk.UseCase

import com.checkout.android_sdk.Architecture.UseCase
import com.checkout.android_sdk.Store.DataStore


class CvvInputUseCase(
    private val dataStore: DataStore,
    private val cvv: String,
    private val callback: Callback
) : UseCase {

    override fun execute() {
        dataStore.cardCvv = cvv
        callback.onCvvUpdated(cvv)
    }

    interface Callback {
        fun onCvvUpdated(cvv: String)
    }

}
