package com.checkout.android_sdk.UseCase

import com.checkout.android_sdk.Architecture.UseCase

class CvvFocusChangedUseCase(private val cvv: String,
                             private val hasFocus: Boolean,
                             private val callback: Callback): UseCase {

    override fun execute() {
        if (hasFocus) {
            callback.onFocusUpdated(false)
        } else if (cvv.length < MIN_CHARACTERS_FOR_VALID_CVV) {
            callback.onFocusUpdated(true)
        }
    }

    companion object {
        private const val MIN_CHARACTERS_FOR_VALID_CVV = 3
    }

    interface Callback {
        fun onFocusUpdated(showError: Boolean)
    }
}
