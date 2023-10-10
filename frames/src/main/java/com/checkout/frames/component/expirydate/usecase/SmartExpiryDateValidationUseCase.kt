package com.checkout.frames.component.expirydate.usecase

import com.checkout.base.usecase.UseCase
import com.checkout.frames.component.expirydate.model.SmartExpiryDateValidationRequest
import com.checkout.frames.utils.constants.EXPIRY_DATE_PREFIX_ZERO
import com.checkout.frames.utils.constants.EXPIRY_DATE_ZERO_POSITION_CHECK
import com.checkout.frames.utils.extensions.isDateInThePastError
import com.checkout.validation.api.CardValidator
import com.checkout.validation.model.ValidationResult

internal class SmartExpiryDateValidationUseCase(private val cardValidator: CardValidator) :
    UseCase<SmartExpiryDateValidationRequest, ValidationResult<String>> {

    override fun execute(data: SmartExpiryDateValidationRequest): ValidationResult<String> {
        val isZeroPrefixExist = data.inputExpiryDate.isNotEmpty() &&
            data.inputExpiryDate[0] > EXPIRY_DATE_ZERO_POSITION_CHECK

        return if (data.isFocused) {
            validateFocusedExpiryDate(data.inputExpiryDate, isZeroPrefixExist)
        } else {
            validateExpiryDate(data.inputExpiryDate, isZeroPrefixExist)
        }
    }

    private fun validateFocusedExpiryDate(
        date: String,
        isZeroPrefixExist: Boolean,
    ): ValidationResult<String> {
        val validationResult = validateExpiryDate(date, isZeroPrefixExist)
        return if (validationResult.isDateInThePastError()) validationResult else ValidationResult.Success(date)
    }

    @Suppress("MagicNumber")
    private fun validateExpiryDate(
        date: String,
        isZeroPrefixExist: Boolean,
    ): ValidationResult<String> {
        val inputMonth = if (isZeroPrefixExist) EXPIRY_DATE_PREFIX_ZERO + date.take(1) else date.take(2)
        val inputYear = if (isZeroPrefixExist) date.dropSafe(1) else date.dropSafe(2)

        return when (val result = cardValidator.validateExpiryDate(inputMonth, inputYear)) {
            is ValidationResult.Success -> ValidationResult.Success(date)
            is ValidationResult.Failure -> result
        }
    }

    @SuppressWarnings("SwallowedException")
    private fun String.dropSafe(n: Int): String = try {
        this.drop(n)
    } catch (e: StringIndexOutOfBoundsException) {
        "0"
    }
}
