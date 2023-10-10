package com.checkout.tokenization.usecase

import com.checkout.base.usecase.UseCase
import com.checkout.tokenization.model.ValidateCVVTokenizationRequest
import com.checkout.validation.api.CVVComponentValidator
import com.checkout.validation.error.ValidationError
import com.checkout.validation.model.ValidationResult

internal class ValidateCVVTokenizationDataUseCase(
    private val cvvComponentValidator: CVVComponentValidator,
) : UseCase<ValidateCVVTokenizationRequest, ValidationResult<Unit>> {

    override fun execute(data: ValidateCVVTokenizationRequest): ValidationResult<Unit> {
        with(cvvComponentValidator.validate(data.cvv, data.cardScheme)) {
            if (this is ValidationResult.Failure || data.cvv.isEmpty()) {
                return ValidationResult.Failure(
                    ValidationError(
                        errorCode = ValidationError.CVV_INVALID_LENGTH,
                        message = "Please enter a valid security code",
                    ),
                )
            }
        }

        return ValidationResult.Success(Unit)
    }
}
