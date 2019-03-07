package com.checkout.sdk.cvvinput

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.store.DataStore

open class CvvFocusChangedUseCase(private val cvv: String,
                             private val hasFocus: Boolean,
                             private val dataStore: DataStore): UseCase<Boolean> {

    override fun execute(): Boolean {
        return !hasFocus && cvv.length < dataStore.cvvLength
    }
}
