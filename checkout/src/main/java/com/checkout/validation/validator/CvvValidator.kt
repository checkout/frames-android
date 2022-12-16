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
        validateCvv(data.cvv, data.cardScheme.cvvLength)

        ValidationResult.Success(Unit)
    } catch (e: ValidationError) {
        ValidationResult.Failure(e)
    }

    /**
     * Checks if the given [cvv] is supported.
     *
     * @throws [ValidationError] if [cvv] contains non digits or length of it not in supported [cvvLength].
     */
    @Throws(ValidationError::class)
    private fun validateCvv(cvv: String, cvvLength: Set<Int>) {
        when {
            cvv.any { !it.isDigit() } -> throw ValidationError(
                ValidationError.CVV_CONTAINS_NON_DIGITS,
                "CVV should contain only digits"
            )

            cvvLength.any { it == cvv.length } -> return

            cvv.length < cvvLength.min() || cvv.length < cvvLength.max() -> throw ValidationError(
                ValidationError.CVV_INCOMPLETE_LENGTH,
                "Incomplete CVV length, it should be $cvvLength"
            )

            else -> throw ValidationError(
                ValidationError.CVV_INVALID_LENGTH,
                "Invalid CVV length, it should be $cvvLength"
            )
        }
    }
}
