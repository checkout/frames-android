package com.checkout.android_sdk.UseCase

import com.checkout.android_sdk.Architecture.UseCase
import com.checkout.android_sdk.Store.DataStore


class CvvInputUseCase(
    private val dataStore: DataStore,
    private val cvv: String
) : UseCase<Boolean> {

    override fun execute(): Boolean {
        dataStore.cardCvv = cvv
        return false
    }
}
