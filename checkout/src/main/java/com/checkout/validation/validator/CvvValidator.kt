package com.checkout.validation.validator

import com.checkout.validation.error.ValidationError
import com.checkout.validation.model.CvvValidationRequest
import com.checkout.validation.model.ValidationResult
import com.checkout.validation.validator.contract.Validator
import kotlin.jvm.Throws

/**
 * Class used to validate cvv number.
 */
internal class CvvValidator : Validator<CvvValidationRequest, Unit> {

    /**
     * Checks the given [CvvValidationRequest].
     *
     * @param data - The date provided for validation of type [CvvValidationRequest].
     * @return [ValidationResult.Success] response when date is valid or [ValidationResult.Failure].
     */
    override fun validate(data: CvvValidationRequest): ValidationResult<Unit> = try {
        if (data.isEagerValidation)
            validateCvvEager(data.cvv, data.cardScheme.cvvLength)
        else
            validateCvv(data.cvv, data.cardScheme.cvvLength)

        ValidationResult.Success(Unit)
    } catch (e: ValidationError) {
        ValidationResult.Failure(e)
    }

    /**
     * Checks if the given [cvv] is supported.
     *
     * @throws [ValidationError] if [cvv] contains non digits or length of it less than or not in supported [cvvLength].
     */
    @Throws(ValidationError::class)
    private fun validateCvvEager(cvv: String, cvvLength: Set<Int>) = provideCvvValidationResult(cvv, cvvLength, true)

    /**
     * Checks if the given [cvv] is supported.
     *
     * @throws [ValidationError] if [cvv] contains non digits or length of it not in supported [cvvLength].
     */
    @Throws(ValidationError::class)
    private fun validateCvv(cvv: String, cvvLength: Set<Int>) = provideCvvValidationResult(cvv, cvvLength, false)

    private fun isPartialCvvEntry(cvv: String, cvvLength: Set<Int>) = cvv.isNotBlank() && !isComplete(cvv, cvvLength)

    private fun isComplete(cvv: String, cvvLength: Set<Int>) = cvvLength.contains(cvv.length)

    @Throws(ValidationError::class)
    private fun provideCvvValidationResult(cvv: String, cvvLength: Set<Int>, isEager: Boolean) {
        when {
            cvvLength.isEmpty() -> throw ValidationError(
                ValidationError.CVV_INVALID_CARD_SCHEME,
                "Card scheme is invalid"
            )
            cvv.any { !it.isDigit() } -> throw ValidationError(
                ValidationError.CVV_CONTAINS_NON_DIGITS,
                "CVV should contain only digits"
            )
            isEager && cvv.length > cvvLength.last() -> throw ValidationError(
                ValidationError.CVV_INVALID_LENGTH,
                "CVV length is invalid, it length should be $cvvLength"
            )
            isEager && isPartialCvvEntry(cvv, cvvLength) -> throw ValidationError(
                ValidationError.CVV_INCOMPLETE_LENGTH,
                "CVV length is incomplete, it should be $cvvLength"
            )
            !cvvLength.contains(cvv.length) -> throw ValidationError(
                ValidationError.CVV_INVALID_LENGTH,
                "CVV length is invalid, it length should be $cvvLength"
            )
        }
    }
}
