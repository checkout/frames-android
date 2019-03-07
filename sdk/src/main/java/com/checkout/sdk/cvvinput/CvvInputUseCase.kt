package com.checkout.sdk.cvvinput

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.store.DataStore


open class CvvInputUseCase(
    private val dataStore: DataStore,
    open val cvv: String
) : UseCase<Boolean> {

    override fun execute(): Boolean {
        dataStore.cardCvv = cvv
        return false
    }
}
