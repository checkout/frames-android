package com.checkout.sdk.cardinput

import android.text.Editable
import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.utils.CardUtils


open class CardInputUseCase(
    open val editableText: Editable,
    private val inMemoryStore: InMemoryStore
) : UseCase<CardUtils.Cards> {

    override fun execute(): CardUtils.Cards {
        // Remove Spaces
        val sanitized = sanitizeEntry(editableText.toString())
        // Format number
        val formatted = CardUtils.getFormattedCardNumber(sanitized)
        // Get Card type
        val cardType = CardUtils.getType(sanitized)
        // Check if card is valid
        val isCardValid = checkIfCardIsValid(sanitized, cardType) // TODO: Move validation

        if (editableText.toString() != formatted) {
            editableText.replace(0, editableText.toString().length, formatted)
        }
        // Save State
        inMemoryStore.cardNumber = sanitized
        inMemoryStore.cvv = inMemoryStore.cvv.copy(expectedLength = cardType.maxCvvLength)

        return cardType
    }

    private fun sanitizeEntry(entry: String): String {
        return entry.replace("\\D".toRegex(), "")
    }

    private fun checkIfCardIsValid(number: String, cardType: CardUtils.Cards): Boolean {
        return CardUtils.isValidCard(number) && hasDesiredLength(number, cardType)
    }

    private fun hasDesiredLength(number: String, cardType: CardUtils.Cards) =
        number.length in cardType.cardLength
}
