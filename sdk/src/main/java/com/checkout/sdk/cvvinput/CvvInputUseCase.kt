package com.checkout.sdk.cvvinput

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.store.DataStore


open class CvvInputUseCase(
    private val dataStore: DataStore,
    private val cvv: String
) : UseCase<Boolean> {

    override fun execute(): Boolean {
        dataStore.cardCvv = cvv
        return false
    }

    open fun getCvv() = cvv
}
