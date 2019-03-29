package com.checkout.sdk.cardinput

import com.checkout.sdk.core.Card
import com.checkout.sdk.utils.CardUtils


data class CardNumber(val value: String) {

    fun isValid(): Boolean {
        val cardType = CardUtils.getType(value)
        return CardUtils.isValidCard(value) && hasDesiredLength(value, cardType)
    }

    private fun hasDesiredLength(number: String, cardType: Card) =
        number.length in cardType.cardLength

    companion object {
        val UNKNOWN = CardNumber("unknown")
    }
}
