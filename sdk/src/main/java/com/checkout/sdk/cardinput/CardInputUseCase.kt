package com.checkout.sdk.cardinput

import android.text.Editable
import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.store.DataStore
import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.utils.CardUtils


open class CardInputUseCase(
    private val editableText: Editable,
    private val dataStore: DataStore,
    private val inMemoryStore: InMemoryStore
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
        inMemoryStore.cvv = inMemoryStore.cvv.copy(expectedLength = cardType.maxCvvLength)

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
