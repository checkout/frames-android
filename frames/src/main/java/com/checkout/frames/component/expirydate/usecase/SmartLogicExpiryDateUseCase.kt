package com.checkout.frames.component.expirydate.usecase

import com.checkout.base.error.CheckoutError
import com.checkout.base.usecase.UseCase
import com.checkout.frames.component.expirydate.model.SmartLogicExpiryDateRequest
import com.checkout.frames.utils.constants.EXPIRY_DATE_MAXIMUM_LENGTH_FOUR
import com.checkout.frames.utils.constants.EXPIRY_DATE_MAXIMUM_LENGTH_THREE
import com.checkout.frames.utils.constants.EXPIRY_DATE_PREFIX_ZERO
import com.checkout.frames.utils.constants.EXPIRY_DATE_ZERO_POSITION_CHECK
import com.checkout.validation.api.CardValidator
import com.checkout.validation.model.ValidationResult

internal class SmartLogicExpiryDateUseCase(private val cardValidator: CardValidator) :
    UseCase<SmartLogicExpiryDateRequest, ValidationResult<String>> {

    override fun execute(data: SmartLogicExpiryDateRequest): ValidationResult<String> {
        val isZeroPrefixExist = data.inputExpiryDate.isNotEmpty() &&
                data.inputExpiryDate[0] > EXPIRY_DATE_ZERO_POSITION_CHECK

        return when {
            data.inputExpiryDate.length == EXPIRY_DATE_MAXIMUM_LENGTH_FOUR ||
                    data.inputExpiryDate.length == EXPIRY_DATE_MAXIMUM_LENGTH_THREE && isZeroPrefixExist ->
                validateExpiryDate(data.inputExpiryDate, isZeroPrefixExist)

            data.isFocusChanged -> {
                ValidationResult.Failure(
                    // TODO: PIMOB-1401 - Implement error handling for expiry date.
                    CheckoutError(
                        errorCode = "",
                        message = ""
                    )
                )
            }

            else -> ValidationResult.Success(data.inputExpiryDate)
        }
    }

    @Suppress("MagicNumber")
    private fun validateExpiryDate(
        data: String,
        isZeroPrefixExist: Boolean,
    ): ValidationResult<String> {
        val inputMonth = if (isZeroPrefixExist) EXPIRY_DATE_PREFIX_ZERO + data.take(1) else data.take(2)
        val inputYear = data.takeLast(2)

        return when (val result = cardValidator.validateExpiryDate(inputMonth, inputYear)) {
            is ValidationResult.Success -> ValidationResult.Success(data)
            is ValidationResult.Failure -> result
        }
    }
}
