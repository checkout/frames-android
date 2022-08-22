package com.checkout.frames.component.expirydate.usecase

import com.checkout.frames.base.usecase.UseCase
import com.checkout.frames.base.validation.ValidateResult
import com.checkout.frames.utils.constants.InputFieldConstants.EXPIRY_DATE_MAXIMUM_LENGTH
import java.util.Calendar

internal class ValidateExpiryDateUseCase : UseCase<StringBuilder, ValidateResult<String>> {

    override fun execute(data: StringBuilder): ValidateResult<String> {
        return validateExpiryDate(data)
    }

    @Suppress("MagicNumber")
    private fun validateExpiryDate(data: StringBuilder): ValidateResult<String> {
        return when {
            data.length == 1 && data[0] > '1' -> {
                ValidateResult.Success("0$data")
            }

            data.length == EXPIRY_DATE_MAXIMUM_LENGTH -> {
                validatePastDate(
                    data.toString(),
                    Calendar.getInstance().get(Calendar.MONTH) + 1,
                    Calendar.getInstance().get(Calendar.YEAR)
                )
            }

            else -> ValidateResult.Success(data.toString())
        }
    }

    @Suppress("MagicNumber")
    private fun validatePastDate(
        data: String,
        currentMonth: Int,
        currentYear: Int,
    ): ValidateResult<String> {
        val twoDigitCurrentYear = currentYear % 100
        val inputMonth = requireNotNull(data.take(2).toIntOrNull())
        val inputYear = requireNotNull(data.takeLast(2).toIntOrNull())

        return if (
            (inputYear - twoDigitCurrentYear) < 0 || (inputYear - twoDigitCurrentYear) == 0 && currentMonth > inputMonth
        )
            ValidateResult.Failure(data.dropLast(1)) else ValidateResult.Success(data)
    }
}
