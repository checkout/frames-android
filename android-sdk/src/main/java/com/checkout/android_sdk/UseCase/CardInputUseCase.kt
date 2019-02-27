package com.checkout.android_sdk.UseCase

import android.text.Editable
import com.checkout.android_sdk.Architecture.UseCase
import com.checkout.android_sdk.Utils.CardUtils


open class CardInputUseCase(private val callback: Callback, private val chars: Editable) :
    UseCase {

    override fun execute() {
        // Remove Spaces
        val sanitized = sanitizeEntry(chars.toString()) // can we just force the keypad
        // Format number
        val formatted = CardUtils.getFormattedCardNumber(sanitized)
        // Get Card type
        val cardType = CardUtils.getType(sanitized)
        // Check if card is valid
        val isCardValid = checkIfCardIsValid(sanitized, cardType)

        if (chars.toString() != formatted) {
            chars.replace(0, chars.toString().length, formatted)
        }

        val cardResult = CardInputResult(sanitized, formatted, cardType, isCardValid)
        callback.onCardInputResult(cardResult)
    }

    private fun sanitizeEntry(entry: String): String {
        return entry.replace("\\D".toRegex(), "")
    }

    interface Callback {
        fun onCardInputResult(cardInputResult: CardInputResult)
    }

    class CardInputResult(
        val cardNumber: String,
        val formattedNumber: String,
        val cardType: CardUtils.Cards,
        val inputFinished: Boolean
    )

    fun checkIfCardIsValid(number: String, cardType: CardUtils.Cards): Boolean {
        var hasDesiredLength = false
        for (i in cardType.cardLength) {
            if (i == number.length) {
                hasDesiredLength = true
                break
            }
        }
        return CardUtils.isValidCard(number) && hasDesiredLength
    }
}
