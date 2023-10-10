package com.checkout.validation.api

import com.checkout.base.model.CardScheme
import com.checkout.tokenization.model.ExpiryDate
import com.checkout.validation.model.ValidationResult

/**
 * Validates individual card details components.
 */
public interface CardValidator {

    /**
     * Validates the given [expiryMonth] & [expiryYear] string values, checking the input is valid
     * and that the representing expiry date is in the future.
     *
     * @param expiryMonth - The month represented as an [Int] value between 1 and 12.
     * @param expiryMonth - The year given in YY or YYYY format.
     * @return [ValidationResult] contains an [ExpiryDate] if the provided values are valid.
     */
    public fun validateExpiryDate(expiryMonth: String, expiryYear: String): ValidationResult<ExpiryDate>

    /**
     * Validates the given [expiryMonth] & [expiryYear] integer values, checking the input is valid
     * and that the representing expiry date is in the future.
     *
     * @param expiryMonth - The month represented as a value between 1 and 12.
     * @param expiryMonth - The year given in YY or YYYY format.
     * @return [ValidationResult] contains an [ExpiryDate] if the provided values are valid.
     */
    public fun validateExpiryDate(expiryMonth: Int, expiryYear: Int): ValidationResult<ExpiryDate>

    /**
     * Validates the given [cvv] according to the [cardScheme].
     *
     * @param cvv - The cvv number represented as [String], should be digits only.
     * @param cardScheme - The card scheme represented as [CardScheme].
     * @return [ValidationResult] with [ValidationResult.Success] or [ValidationResult.Failure].
     */
    public fun validateCvv(cvv: String, cardScheme: CardScheme): ValidationResult<Unit>

    /**
     * Checks whether a given [cardNumber] is valid and matches any of the supported [CardScheme].
     * Must also pass luhn and length checks.
     *
     * @param cardNumber - The card number presented as a [String], should be digits only.
     * @return [ValidationResult] contains a [CardScheme] if card scheme can be determined.
     */
    public fun validateCardNumber(cardNumber: String): ValidationResult<CardScheme>

    /**
     * Checks whether a given [cardNumber] is valid and matches any of the supported [CardScheme].
     *
     * @param cardNumber - The card number presented as a [String], should be digits only.
     * @return [ValidationResult] contains a [CardScheme] if card scheme can be determined.
     */
    public fun eagerValidateCardNumber(cardNumber: String): ValidationResult<CardScheme>
}
