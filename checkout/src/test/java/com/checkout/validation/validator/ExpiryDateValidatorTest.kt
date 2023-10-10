package com.checkout.validation.validator

import android.annotation.SuppressLint
import com.checkout.tokenization.model.ExpiryDate
import com.checkout.validation.error.ValidationError
import com.checkout.validation.model.ExpiryDateValidationRequest
import com.checkout.validation.model.ValidationResult
import com.checkout.validation.validator.contract.Validator
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.Instant
import java.time.Year
import java.util.Date
import java.util.stream.Stream

@SuppressLint("NewApi")
internal class ExpiryDateValidatorTest {

    private lateinit var expiryDateValidator: Validator<ExpiryDateValidationRequest, ExpiryDate>
    private lateinit var testDate: Date

    @BeforeEach
    internal fun setUp() {
        expiryDateValidator = ExpiryDateValidator()

        testDate = Date.from(Instant.parse("2021-11-22T10:15:30.00Z"))
    }

    @Test
    fun `given valid month & year returns Success result`() {
        // Given
        val testMonth = "12"
        val testYear = Year.now().toString()

        // When
        val expiryDateValidationResult = expiryDateValidator.validate(ExpiryDateValidationRequest(testMonth, testYear))

        // Then
        when (expiryDateValidationResult) {
            is ValidationResult.Success -> {
                expiryDateValidationResult.value.expiryMonth `should be equal to` 12
                expiryDateValidationResult.value.expiryYear `should be equal to` Year.now().value
            }
            is ValidationResult.Failure -> {
                fail(expiryDateValidationResult.error)
            }
        }
    }

    @Test
    fun `given valid month & year in the past returns Failure result`() {
        // Given
        val testMonth = "12"
        val testYear = "1999"
        val expectedErrorMsg = "Expiry month $testMonth & year $testYear should be in the future"

        // When
        val expiryDateValidationResult = expiryDateValidator.validate(ExpiryDateValidationRequest(testMonth, testYear))

        // Then
        assertTrue(expiryDateValidationResult is ValidationResult.Failure)
        assertTrue((expiryDateValidationResult as ValidationResult.Failure).error is ValidationError)
        expiryDateValidationResult.error.message `should be equal to` expectedErrorMsg
    }

    @ParameterizedTest(name = "Expect {2} => given month={0} & year={1}")
    @MethodSource(
        "invalidMonthStringArguments",
        "invalidYearStringArguments",
        "invalidExpiryDateArguments",
    )
    fun `given month or year is not valid returns failure`(
        testMonth: String,
        testYear: String,
        expectedErrorCode: String,
    ) {
        // When
        val expiryDateValidationResult = expiryDateValidator.validate(ExpiryDateValidationRequest(testMonth, testYear))

        // Then
        when (expiryDateValidationResult) {
            is ValidationResult.Success -> {
                fail("Expected an error result but received ${expiryDateValidationResult.value}")
            }
            is ValidationResult.Failure -> {
                expiryDateValidationResult.error.errorCode `should be equal to` expectedErrorCode
            }
        }
    }

    companion object {

        @JvmStatic
        fun invalidMonthStringArguments(): Stream<Arguments> = Stream.of(
            // Invalid month string
            Arguments.of("", "2021", ValidationError.INVALID_MONTH_STRING),
            Arguments.of("1.2", "2021", ValidationError.INVALID_MONTH_STRING),
            Arguments.of("asdsa", "2021", ValidationError.INVALID_MONTH_STRING),
            Arguments.of("as1d3sa", "2021", ValidationError.INVALID_MONTH_STRING),
            Arguments.of("10.", "2021", ValidationError.INVALID_MONTH_STRING),
            Arguments.of("-asd", "2021", ValidationError.INVALID_MONTH_STRING),
            Arguments.of("1 1", "2021", ValidationError.INVALID_MONTH_STRING),
            Arguments.of("11 ", "2021", ValidationError.INVALID_MONTH_STRING),
        )

        @JvmStatic
        fun invalidYearStringArguments(): Stream<Arguments> = Stream.of(
            Arguments.of("11", "", ValidationError.INVALID_YEAR_STRING),
            Arguments.of("11", "1.2", ValidationError.INVALID_YEAR_STRING),
            Arguments.of("11", "asdsa", ValidationError.INVALID_YEAR_STRING),
            Arguments.of("11", "as1d3sa", ValidationError.INVALID_YEAR_STRING),
            Arguments.of("11", "10.", ValidationError.INVALID_YEAR_STRING),
            Arguments.of("11", "-asd", ValidationError.INVALID_YEAR_STRING),
            Arguments.of("11", "20 21", ValidationError.INVALID_YEAR_STRING),
            Arguments.of("11", "2021 ", ValidationError.INVALID_YEAR_STRING),
        )

        @JvmStatic
        fun invalidExpiryDateArguments(): Stream<Arguments> = Stream.of(
            Arguments.of("0", "2021", ValidationError.INVALID_MONTH),
            Arguments.of("13", "2021", ValidationError.INVALID_MONTH),
            Arguments.of("-5", "2021", ValidationError.INVALID_MONTH),

            Arguments.of("11", "-19", ValidationError.INVALID_YEAR),
            Arguments.of("11", "-2000", ValidationError.INVALID_YEAR),
            Arguments.of("11", "2", ValidationError.INVALID_YEAR),
            Arguments.of("11", "202", ValidationError.INVALID_YEAR),

            Arguments.of("11", "0", ValidationError.EXPIRY_DATE_IN_PAST),
            Arguments.of("10", "20", ValidationError.EXPIRY_DATE_IN_PAST),
            Arguments.of("10", "2021", ValidationError.EXPIRY_DATE_IN_PAST),
        )
    }
}
