package com.checkout.android_sdk.UseCase


import com.checkout.android_sdk.Architecture.UseCase
import com.checkout.android_sdk.Utils.CardUtils

class CardFocusUseCase(
    private val hasFocus: Boolean,
    private val cardNumber: String?,
    private val callback: Callback
): UseCase {

    override fun execute() {
        if (hasFocus) {
            // Clear the error message until the field loses focus
            callback.onCardFocusResult(false)
        } else {
            if (!CardUtils.isValidCard(cardNumber)) {
                callback.onCardFocusResult(true)
            }
        }
    }

    interface Callback {
        fun onCardFocusResult(cardError: Boolean)
    }

}
