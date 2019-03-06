package com.checkout.sdk.UseCase

import com.checkout.sdk.Architecture.UseCase
import com.checkout.sdk.Store.DataStore

class CvvFocusChangedUseCase(private val cvv: String,
                             private val hasFocus: Boolean,
                             private val dataStore: DataStore): UseCase<Boolean> {

    override fun execute(): Boolean {
        return !hasFocus && cvv.length < dataStore.cvvLength
    }
}
