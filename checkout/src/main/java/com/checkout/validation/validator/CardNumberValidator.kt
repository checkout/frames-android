package com.checkout.validation.validator

import com.checkout.base.model.CardScheme
import com.checkout.validation.error.ValidationError
import com.checkout.validation.model.CardNumberValidationRequest
import com.checkout.validation.model.ValidationResult
import com.checkout.validation.validator.contract.Checker
import com.checkout.validation.validator.contract.Validator

/**
 * Class used to validate card number.
 */
internal class CardNumberValidator(
    private val checker: Checker<String>
) : Validator<CardNumberValidationRequest, CardScheme> {

    /**
     * Checks the given [CardNumberValidationRequest].
     *
     * @param data - The data provided for validation of type [CardNumberValidationRequest].
     * @return [ValidationResult.Success] with [CardScheme] when date is valid or [ValidationResult.Failure].
     */
    override fun validate(data: CardNumberValidationRequest): ValidationResult<CardScheme> = try {
        // Remove all spaces, new lines and tabs
        val whitespacesRegex = Regex("\\s")
        val cardNumber = data.cardNumber.replace(whitespacesRegex, "")
        val cardScheme = if (data.isEagerValidation) validateEager(cardNumber) else validate(cardNumber)

        ValidationResult.Success(cardScheme)
    } catch (e: ValidationError) {
        ValidationResult.Failure(e)
    }

    /**
     * Checks whether a given card number is valid and matches any of the supported schemes,
     * pass luhn and length checks.
     *
     * @param cardNumber - The card number provided for validation of type [String].
     * @return [CardScheme].
     */
    @Throws(ValidationError::class)
    private fun validate(cardNumber: String): CardScheme {
        val cardScheme = provideCardScheme(cardNumber, false)
        return if (checker.check(cardNumber)) cardScheme else CardScheme.UNKNOWN
    }

    /**
     * Checks whether a given card number is valid and matches any of the supported schemes.
     *
     * @param cardNumber - The card number provided for validation of type [String].
     * @return [CardScheme].
     */
    @Throws(ValidationError::class)
    private fun validateEager(cardNumber: String): CardScheme = provideCardScheme(cardNumber, true)

    @Throws(ValidationError::class)
    private fun provideCardScheme(cardNumber: String, isEager: Boolean): CardScheme {
        if (cardNumber.any { !it.isDigit() }) throw ValidationError(
            ValidationError.CARD_NUMBER_INVALID_CHARACTERS,
            "Card number should contain only digits"
        )

        CardScheme.values().forEach {
            val regex = if (isEager) it.eagerRegex else it.regex
            if (cardNumber.matches(regex)) return it
        }

        return CardScheme.UNKNOWN
    }
}
