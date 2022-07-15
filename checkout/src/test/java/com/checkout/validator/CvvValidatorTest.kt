package com.checkout.validation.validator

import android.annotation.SuppressLint
import com.checkout.base.model.CardScheme
import com.checkout.validation.error.ValidationError
import com.checkout.validation.model.CvvValidationRequest
import com.checkout.validation.model.ValidationResult
import com.checkout.validation.validator.contract.Validator
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

import java.util.stream.Stream

@SuppressLint("NewApi")
internal class CvvValidatorTest {

    private lateinit var cvvValidator: Validator<CvvValidationRequest, Unit>

    @BeforeEach
    fun setUp() {
        cvvValidator = CvvValidator()
    }

    @ParameterizedTest(name = "Expect {2} => given cvv={0} & cardScheme={1}")
    @MethodSource(
        "invalidCvvArguments",
        "validCvvArguments",
        "invalidCardSchemeArguments"
    )
    fun `Validation of given cvv and card scheme returns correct validation result`(
        cvv: String,
        cardScheme: CardScheme,
        expectedResult: ValidationResult<Unit>
    ) {
        // When
        val result = cvvValidator.validate(CvvValidationRequest(cvv, cardScheme))

        // Then
        when (expectedResult) {
            is ValidationResult.Success -> assertTrue(result is ValidationResult.Success<Unit>)
            is ValidationResult.Failure -> assertEquals(
                expectedResult.error.errorCode,
                (result as ValidationResult.Failure).error.errorCode
            )
        }
    }

    companion object {
        @JvmStatic
        fun invalidCvvArguments(): Stream<Arguments> = Stream.of(
            Arguments.of("", CardScheme.AMERICAN_EXPRESS, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("123", CardScheme.AMERICAN_EXPRESS, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12345", CardScheme.AMERICAN_EXPRESS, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12 ", CardScheme.AMERICAN_EXPRESS, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),
            Arguments.of("12q", CardScheme.AMERICAN_EXPRESS, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),

            Arguments.of("", CardScheme.VISA, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("1234", CardScheme.VISA, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12", CardScheme.VISA, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12 ", CardScheme.VISA, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),
            Arguments.of("12q", CardScheme.VISA, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),

            Arguments.of("", CardScheme.JCB, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("1234", CardScheme.JCB, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12", CardScheme.JCB, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12 ", CardScheme.JCB, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),
            Arguments.of("12q", CardScheme.JCB, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),

            Arguments.of("", CardScheme.MADA, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("1234", CardScheme.MADA, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12", CardScheme.MADA, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12 ", CardScheme.MADA, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),
            Arguments.of("12q", CardScheme.MADA, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),

            Arguments.of("1234", CardScheme.MAESTRO, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12", CardScheme.MAESTRO, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12 ", CardScheme.MAESTRO, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),
            Arguments.of("12q", CardScheme.MAESTRO, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),

            Arguments.of("", CardScheme.DINERS_CLUB, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("1234", CardScheme.DINERS_CLUB, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12", CardScheme.DINERS_CLUB, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12 ", CardScheme.DINERS_CLUB, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),
            Arguments.of("12q", CardScheme.DINERS_CLUB, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),

            Arguments.of("", CardScheme.DISCOVER, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("1234", CardScheme.DISCOVER, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12", CardScheme.DISCOVER, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12 ", CardScheme.DISCOVER, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),
            Arguments.of("12q", CardScheme.DISCOVER, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),

            Arguments.of("", CardScheme.MASTERCARD, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("1234", CardScheme.MASTERCARD, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12", CardScheme.MASTERCARD, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12 ", CardScheme.MASTERCARD, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),
            Arguments.of("12q", CardScheme.MASTERCARD, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS))

        )

        @JvmStatic
        fun validCvvArguments(): Stream<Arguments> = Stream.of(
            Arguments.of("1234", CardScheme.AMERICAN_EXPRESS, provideSuccess()),
            Arguments.of("123", CardScheme.DINERS_CLUB, provideSuccess()),
            Arguments.of("321", CardScheme.DISCOVER, provideSuccess()),
            Arguments.of("111", CardScheme.JCB, provideSuccess()),
            Arguments.of("000", CardScheme.MADA, provideSuccess()),
            Arguments.of("", CardScheme.MAESTRO, provideSuccess()),
            Arguments.of("444", CardScheme.MAESTRO, provideSuccess()),
            Arguments.of("435", CardScheme.MASTERCARD, provideSuccess()),
            Arguments.of("567", CardScheme.VISA, provideSuccess())
        )

        @JvmStatic
        fun invalidCardSchemeArguments(): Stream<Arguments> = Stream.of(
            Arguments.of("", CardScheme.UNKNOWN, provideFailure(ValidationError.CVV_INVALID_CARD_SCHEME)),
            Arguments.of("12", CardScheme.UNKNOWN, provideFailure(ValidationError.CVV_INVALID_CARD_SCHEME)),
            Arguments.of("123", CardScheme.UNKNOWN, provideFailure(ValidationError.CVV_INVALID_CARD_SCHEME)),
            Arguments.of("1234", CardScheme.UNKNOWN, provideFailure(ValidationError.CVV_INVALID_CARD_SCHEME)),
        )

        private fun provideFailure(errorCode: String) = ValidationResult.Failure(ValidationError(errorCode))

        private fun provideSuccess() = ValidationResult.Success(Unit)
    }
}
