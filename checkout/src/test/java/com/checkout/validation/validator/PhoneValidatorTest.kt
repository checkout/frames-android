package com.checkout.validation.validator

import android.annotation.SuppressLint
import com.checkout.base.model.Country
import com.checkout.tokenization.model.Phone
import com.checkout.validation.error.ValidationError
import com.checkout.validation.model.PhoneValidationRequest
import com.checkout.validation.model.ValidationResult
import com.checkout.validation.validator.contract.Validator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@SuppressLint("NewApi")
internal class PhoneValidatorTest {

    private lateinit var phoneValidator: Validator<PhoneValidationRequest, Phone>

    @BeforeEach
    fun setUp() {
        phoneValidator = PhoneValidator()
    }

    @ParameterizedTest
    @MethodSource(
        "invalidPhoneArguments",
        "validPhoneArguments",
    )
    fun `Validation of given phone returns correct validation result`(
        testNumber: String,
        testCountry: Country?,
        expectedResult: ValidationResult<Phone>,
    ) {
        // Given
        val phoneValidationRequest = PhoneValidationRequest(
            testNumber,
            testCountry,
        )

        // When
        val result = phoneValidator.validate(phoneValidationRequest)

        // Then
        when (expectedResult) {
            is ValidationResult.Success -> {
                val resultPhoneValidation = (result as? ValidationResult.Success<Phone>)?.value
                Assertions.assertEquals(expectedResult.value, resultPhoneValidation)
            }

            is ValidationResult.Failure -> Assertions.assertEquals(
                expectedResult.error.errorCode,
                (result as? ValidationResult.Failure)?.error?.errorCode,
            )
        }
    }

    companion object {
        @JvmStatic
        fun invalidPhoneArguments(): Stream<Arguments> = Stream.of(
            Arguments.of(
                "74049",
                Country.from("GB"),
                provideFailure(ValidationError.NUMBER_INCORRECT_LENGTH),
            ),
            Arguments.of(
                "+44 7404953067462348736428472364",
                Country.from("GB"),
                provideFailure(ValidationError.NUMBER_INCORRECT_LENGTH),
            ),
        )

        @JvmStatic
        fun validPhoneArguments(): Stream<Arguments> = Stream.of(
            Arguments.of(
                "+44 7404953067",
                Country.from("GB"),
                provideSuccess(Phone("+44 7404953067", Country.from("GB"))),
            ),
            Arguments.of(
                "7404953067",
                Country.from(""),
                provideSuccess(Phone("7404953067", Country.from(""))),
            ),
        )

        private fun provideFailure(errorCode: String) = ValidationResult.Failure(ValidationError(errorCode))

        private fun provideSuccess(phone: Phone) = ValidationResult.Success(phone)
    }
}
