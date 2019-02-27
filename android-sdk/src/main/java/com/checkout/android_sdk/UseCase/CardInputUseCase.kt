package com.checkout.android_sdk.UseCase

import android.text.Editable
import com.checkout.android_sdk.Architecture.UseCase
import com.checkout.android_sdk.Utils.CardUtils


open class CardInputUseCase(private val callback: Callback, private val editableText: Editable) :
    UseCase {

    override fun execute() {
        // Remove Spaces
        val sanitized = sanitizeEntry(editableText.toString()) // can we just force the keypad
        // Format number
        val formatted = CardUtils.getFormattedCardNumber(sanitized)
        // Get Card type
        val cardType = CardUtils.getType(sanitized)
        // Check if card is valid
        val isCardValid = checkIfCardIsValid(sanitized, cardType)

        if (editableText.toString() != formatted) {
            editableText.replace(0, editableText.toString().length, formatted)
        }

        val cardResult = CardInputResult(sanitized, cardType, isCardValid)
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
        val cardType: CardUtils.Cards,
        val inputFinished: Boolean
    )

    private fun checkIfCardIsValid(number: String, cardType: CardUtils.Cards): Boolean {
        return CardUtils.isValidCard(number) && hasDesiredLength(number, cardType)
    }

    private fun hasDesiredLength(number: String, cardType: CardUtils.Cards) =
        number.length in cardType.cardLength
}
