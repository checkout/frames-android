package com.checkout.android_sdk.UseCase

import com.checkout.android_sdk.Architecture.UseCase
import com.checkout.android_sdk.Store.DataStore

class CvvFocusChangedUseCase(private val cvv: String,
                             private val hasFocus: Boolean,
                             private val dataStore: DataStore,
                             private val callback: Callback): UseCase {

    override fun execute() {
        if (hasFocus) {
            callback.onFocusUpdated(false)
        } else if (cvv.length < dataStore.cvvLength) {
            callback.onFocusUpdated(true)
        }
    }

    interface Callback {
        fun onFocusUpdated(showError: Boolean)
    }
}
