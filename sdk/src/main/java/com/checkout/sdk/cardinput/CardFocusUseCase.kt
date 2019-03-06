package com.checkout.sdk.cardinput


import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.utils.CardUtils

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
