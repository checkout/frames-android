package com.checkout.sdk.UseCase

import com.checkout.sdk.Architecture.UseCase
import com.checkout.sdk.Store.DataStore


class CvvInputUseCase(
    private val dataStore: DataStore,
    private val cvv: String
) : UseCase<Boolean> {

    override fun execute(): Boolean {
        dataStore.cardCvv = cvv
        return false
    }
}
