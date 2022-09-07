package com.checkout.validation.validator

import com.checkout.tokenization.model.ExpiryDate
import com.checkout.validation.error.ValidationError
import com.checkout.validation.model.ExpiryDateValidationRequest
import com.checkout.validation.model.ValidationResult
import com.checkout.validation.validator.contract.Validator
import java.util.Calendar
import java.util.Date
import kotlin.jvm.Throws

/**
 * Class used to validate expiry date.
 */
internal class ExpiryDateValidator : Validator<ExpiryDateValidationRequest, ExpiryDate> {

    /**
     * Checks the given [ExpiryDateValidationRequest].
     *
     * @param data - The date provided for validation of type [ExpiryDateValidationRequest].
     * @return [ValidationResult.Success] with [ExpiryDate] response when date is valid or [ValidationResult.Failure].
     */
    override fun validate(data: ExpiryDateValidationRequest): ValidationResult<ExpiryDate> = try {
        val expiryDate = ExpiryDate(
            provideValidatedMonth(data.expiryMonth),
            provideValidated4DigitYear(data.expiryYear)
        )

        validateExpiryDate(expiryDate)

        ValidationResult.Success(expiryDate)
    } catch (e: ValidationError) {
        ValidationResult.Failure(e)
    }

    /**
     * Validate and convert the given string to an [Int] value representing the month
     *
     * @return An integer if the input string is a valid month value.
     * @throws [ValidationError] if the input is not valid.
     */
    @Throws(ValidationError::class)
    private fun provideValidatedMonth(value: String): Int = when (val month = value.toIntOrNull()) {
        null -> throw ValidationError(
            ValidationError.INVALID_MONTH_STRING,
            "Invalid value provided for month: $month"
        )
        !in MONTH_RANGE -> throw ValidationError(
            ValidationError.INVALID_MONTH,
            "Month must be >= ${MONTH_RANGE.first} && <= ${MONTH_RANGE.last}"
        )
        else -> month
    }

    /**
     * Validate and convert the given string to an [Int] value representing a year in 4 digit format.
     *
     * @return An integer if the input string is a valid 2 or 4 year value. (i.e. "21" or "2021" returns 2021)
     * @throws [ValidationError] if the input is not valid.
     */
    @Throws(ValidationError::class)
    private fun provideValidated4DigitYear(value: String, date: Date = Date()): Int {
        val utcCalendar = Calendar.getInstance().apply { time = date }
        val referenceYear = utcCalendar.get(Calendar.YEAR).toString().takeLast(2)
        val year = value.toIntOrNull()

        return when {
            year == null -> throw ValidationError(
                ValidationError.INVALID_YEAR_STRING,
                "Invalid value provided for year: $value"
            )
            year < 0 -> throw ValidationError(
                ValidationError.INVALID_YEAR,
                "Year cannot be a negative value: $value"
            )
            (value.length == 1 && value.first() < referenceYear.first()) || value.length == YEAR_SHORT_FORMAT ->
                year + YEAR_LONG_DELTA // Convert 2 digit year to 4 digit year
            value.length == YEAR_LONG_FORMAT -> year
            else -> throw ValidationError(
                ValidationError.INVALID_YEAR,
                "Unexpected year value detected: $value"
            )
        }
    }

    /**
     * Checks if the given [expiryDate] represents a date on or after the given reference [date].
     *
     * @throws [ValidationError] if [expiryDate] is before [date].
     */
    @Throws(ValidationError::class)
    private fun validateExpiryDate(expiryDate: ExpiryDate, date: Date = Date()) {
        val (month, year) = expiryDate
        val utcCalendar = Calendar.getInstance().apply { time = date }
        val referenceYear = utcCalendar.get(Calendar.YEAR)
        val referenceMonth = utcCalendar.get(Calendar.MONTH) + 1

        if (year < referenceYear || (year == referenceYear && month < referenceMonth)) {
            throw ValidationError(
                ValidationError.EXPIRY_DATE_IN_PAST,
                "Expiry month $month & year $year should be in the future"
            )
        }
    }

    private companion object {
        const val YEAR_SHORT_FORMAT = 2
        const val YEAR_LONG_FORMAT = 4
        const val YEAR_LONG_DELTA = 2000
        val MONTH_RANGE = 1..12
    }
}
