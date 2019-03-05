package com.checkout.android_sdk.UseCase


import com.checkout.android_sdk.Architecture.UseCase
import com.checkout.android_sdk.Utils.CardUtils

class CardFocusUseCase(
    private val hasFocus: Boolean,
    private val cardNumber: String?
): UseCase<Boolean> {

    override fun execute(): Boolean {
        if (hasFocus) {
            // Clear the error message until the field loses focus
            return false
        } else {
            if (!CardUtils.isValidCard(cardNumber)) {
                return true
            }
            return false
        }
    }
}
