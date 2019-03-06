package com.checkout.android_sdk.UseCase

import android.text.Editable
import com.checkout.android_sdk.Architecture.UseCase
import com.checkout.android_sdk.Store.DataStore
import com.checkout.android_sdk.Utils.CardUtils


open class CardInputUseCase(
    private val editableText: Editable,
    private val dataStore: DataStore
) : UseCase<CardInputUseCase.CardInputResult> {

    override fun execute(): CardInputResult {
        // Remove Spaces
        val sanitized = sanitizeEntry(editableText.toString())
        // Format number
        val formatted = CardUtils.getFormattedCardNumber(sanitized)
        // Get Card type
        val cardType = CardUtils.getType(sanitized)
        // Check if card is valid
        val isCardValid = checkIfCardIsValid(sanitized, cardType)

        if (editableText.toString() != formatted) {
            editableText.replace(0, editableText.toString().length, formatted)
        }
        // Save State
        dataStore.cardNumber = sanitized
        dataStore.cvvLength = cardType.maxCvvLength

        return CardInputResult(sanitized, cardType, isCardValid, false)
    }

    private fun sanitizeEntry(entry: String): String {
        return entry.replace("\\D".toRegex(), "")
    }

    private fun checkIfCardIsValid(number: String, cardType: CardUtils.Cards): Boolean {
        return CardUtils.isValidCard(number) && hasDesiredLength(number, cardType)
    }

    private fun hasDesiredLength(number: String, cardType: CardUtils.Cards) =
        number.length in cardType.cardLength

    data class CardInputResult(
        val cardNumber: String,
        val cardType: CardUtils.Cards,
        val inputFinished: Boolean,
        val showError: Boolean
    )
}
