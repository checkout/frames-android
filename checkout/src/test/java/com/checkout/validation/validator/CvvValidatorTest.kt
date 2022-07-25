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
        "invalidCardSchemeArguments",
        "eagerInvalidCvvArguments",
        "eagerValidCvvArguments",
        "eagerInvalidCardSchemeArguments"

    )
    fun `Validation of given cvv and card scheme returns correct validation result`(
        cvv: String,
        cardScheme: CardScheme,
        isEager: Boolean,
        expectedResult: ValidationResult<Unit>,
    ) {
        // When
        val result = cvvValidator.validate(CvvValidationRequest(cvv, cardScheme, isEager))

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
            Arguments.of(
                "",
                CardScheme.AMERICAN_EXPRESS,
                false,
                provideFailure(ValidationError.CVV_INVALID_LENGTH)
            ),
            Arguments.of(
                "123",
                CardScheme.AMERICAN_EXPRESS,
                false,
                provideFailure(ValidationError.CVV_INVALID_LENGTH)
            ),
            Arguments.of(
                "12345",
                CardScheme.AMERICAN_EXPRESS,
                false,
                provideFailure(ValidationError.CVV_INVALID_LENGTH)
            ),
            Arguments.of(
                "12 ",
                CardScheme.AMERICAN_EXPRESS,
                false,
                provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)
            ),
            Arguments.of(
                "12q",
                CardScheme.AMERICAN_EXPRESS,
                false,
                provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)
            ),

            Arguments.of("", CardScheme.VISA, false, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("1234", CardScheme.VISA, false, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12", CardScheme.VISA, false, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12 ", CardScheme.VISA, false, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),
            Arguments.of("12q", CardScheme.VISA, false, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),

            Arguments.of("", CardScheme.JCB, false, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("1234", CardScheme.JCB, false, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12", CardScheme.JCB, false, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12 ", CardScheme.JCB, false, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),
            Arguments.of("12q", CardScheme.JCB, false, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),

            Arguments.of("", CardScheme.MADA, false, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("1234", CardScheme.MADA, false, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12", CardScheme.MADA, false, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12 ", CardScheme.MADA, false, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),
            Arguments.of("12q", CardScheme.MADA, false, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),

            Arguments.of("1234", CardScheme.MAESTRO, false, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12", CardScheme.MAESTRO, false, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12 ", CardScheme.MAESTRO, false, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),
            Arguments.of("12q", CardScheme.MAESTRO, false, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),

            Arguments.of("", CardScheme.DINERS_CLUB, false, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("1234", CardScheme.DINERS_CLUB, false, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12", CardScheme.DINERS_CLUB, false, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12 ", CardScheme.DINERS_CLUB, false, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),
            Arguments.of("12q", CardScheme.DINERS_CLUB, false, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),

            Arguments.of("", CardScheme.DISCOVER, false, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("1234", CardScheme.DISCOVER, false, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12", CardScheme.DISCOVER, false, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12 ", CardScheme.DISCOVER, false, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),
            Arguments.of("12q", CardScheme.DISCOVER, false, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),

            Arguments.of("", CardScheme.MASTERCARD, false, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("1234", CardScheme.MASTERCARD, false, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12", CardScheme.MASTERCARD, false, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12 ", CardScheme.MASTERCARD, false, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),
            Arguments.of("12q", CardScheme.MASTERCARD, false, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS))

        )

        @JvmStatic
        fun validCvvArguments(): Stream<Arguments> = Stream.of(
            Arguments.of("1234", CardScheme.AMERICAN_EXPRESS, false, provideSuccess()),
            Arguments.of("123", CardScheme.DINERS_CLUB, false, provideSuccess()),
            Arguments.of("321", CardScheme.DISCOVER, false, provideSuccess()),
            Arguments.of("111", CardScheme.JCB, false, provideSuccess()),
            Arguments.of("000", CardScheme.MADA, false, provideSuccess()),
            Arguments.of("", CardScheme.MAESTRO, false, provideSuccess()),
            Arguments.of("444", CardScheme.MAESTRO, false, provideSuccess()),
            Arguments.of("435", CardScheme.MASTERCARD, false, provideSuccess()),
            Arguments.of("567", CardScheme.VISA, false, provideSuccess())
        )

        @JvmStatic
        fun invalidCardSchemeArguments(): Stream<Arguments> = Stream.of(
            Arguments.of("", CardScheme.UNKNOWN, false, provideFailure(ValidationError.CVV_INVALID_CARD_SCHEME)),
            Arguments.of("12", CardScheme.UNKNOWN, false, provideFailure(ValidationError.CVV_INVALID_CARD_SCHEME)),
            Arguments.of("123", CardScheme.UNKNOWN, false, provideFailure(ValidationError.CVV_INVALID_CARD_SCHEME)),
            Arguments.of("1234", CardScheme.UNKNOWN, false, provideFailure(ValidationError.CVV_INVALID_CARD_SCHEME)),
        )

        @JvmStatic
        fun eagerInvalidCvvArguments(): Stream<Arguments> = Stream.of(
            Arguments.of("", CardScheme.AMERICAN_EXPRESS, true, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of(
                "123",
                CardScheme.AMERICAN_EXPRESS,
                true,
                provideFailure(ValidationError.CVV_INCOMPLETE_LENGTH)
            ),
            Arguments.of(
                "12345",
                CardScheme.AMERICAN_EXPRESS,
                true,
                provideFailure(ValidationError.CVV_INVALID_LENGTH)
            ),
            Arguments.of(
                "12 ",
                CardScheme.AMERICAN_EXPRESS,
                true,
                provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)
            ),
            Arguments.of(
                "12q",
                CardScheme.AMERICAN_EXPRESS,
                true,
                provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)
            ),

            Arguments.of("", CardScheme.VISA, true, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("1234", CardScheme.VISA, true, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12", CardScheme.VISA, true, provideFailure(ValidationError.CVV_INCOMPLETE_LENGTH)),
            Arguments.of("12 ", CardScheme.VISA, true, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),
            Arguments.of("12q", CardScheme.VISA, true, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),

            Arguments.of("", CardScheme.JCB, true, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("1234", CardScheme.JCB, true, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12", CardScheme.JCB, true, provideFailure(ValidationError.CVV_INCOMPLETE_LENGTH)),
            Arguments.of("12 ", CardScheme.JCB, true, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),
            Arguments.of("12q", CardScheme.JCB, true, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),

            Arguments.of("", CardScheme.MADA, true, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("1234", CardScheme.MADA, true, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12", CardScheme.MADA, true, provideFailure(ValidationError.CVV_INCOMPLETE_LENGTH)),
            Arguments.of("12 ", CardScheme.MADA, true, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),
            Arguments.of("12q", CardScheme.MADA, true, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),

            Arguments.of("1234", CardScheme.MAESTRO, true, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12", CardScheme.MAESTRO, true, provideFailure(ValidationError.CVV_INCOMPLETE_LENGTH)),
            Arguments.of("12 ", CardScheme.MAESTRO, true, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),
            Arguments.of("12q", CardScheme.MAESTRO, true, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),

            Arguments.of("", CardScheme.DINERS_CLUB, true, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("1234", CardScheme.DINERS_CLUB, true, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12", CardScheme.DINERS_CLUB, true, provideFailure(ValidationError.CVV_INCOMPLETE_LENGTH)),
            Arguments.of("12 ", CardScheme.DINERS_CLUB, true, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),
            Arguments.of("12q", CardScheme.DINERS_CLUB, true, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),

            Arguments.of("", CardScheme.DISCOVER, true, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("1234", CardScheme.DISCOVER, true, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12", CardScheme.DISCOVER, true, provideFailure(ValidationError.CVV_INCOMPLETE_LENGTH)),
            Arguments.of("12 ", CardScheme.DISCOVER, true, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),
            Arguments.of("12q", CardScheme.DISCOVER, true, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),

            Arguments.of("", CardScheme.MASTERCARD, true, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("1234", CardScheme.MASTERCARD, true, provideFailure(ValidationError.CVV_INVALID_LENGTH)),
            Arguments.of("12", CardScheme.MASTERCARD, true, provideFailure(ValidationError.CVV_INCOMPLETE_LENGTH)),
            Arguments.of("12 ", CardScheme.MASTERCARD, true, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS)),
            Arguments.of("12q", CardScheme.MASTERCARD, true, provideFailure(ValidationError.CVV_CONTAINS_NON_DIGITS))
        )

        @JvmStatic
        fun eagerValidCvvArguments(): Stream<Arguments> = Stream.of(
            Arguments.of("1234", CardScheme.AMERICAN_EXPRESS, true, provideSuccess()),
            Arguments.of("123", CardScheme.DINERS_CLUB, true, provideSuccess()),
            Arguments.of("321", CardScheme.DISCOVER, true, provideSuccess()),
            Arguments.of("111", CardScheme.JCB, true, provideSuccess()),
            Arguments.of("000", CardScheme.MADA, true, provideSuccess()),
            Arguments.of("", CardScheme.MAESTRO, true, provideSuccess()),
            Arguments.of("444", CardScheme.MAESTRO, true, provideSuccess()),
            Arguments.of("435", CardScheme.MASTERCARD, true, provideSuccess()),
            Arguments.of("567", CardScheme.VISA, true, provideSuccess())
        )

        @JvmStatic
        fun eagerInvalidCardSchemeArguments(): Stream<Arguments> = Stream.of(
            Arguments.of("", CardScheme.UNKNOWN, true, provideFailure(ValidationError.CVV_INVALID_CARD_SCHEME)),
            Arguments.of("12", CardScheme.UNKNOWN, true, provideFailure(ValidationError.CVV_INVALID_CARD_SCHEME)),
            Arguments.of("123", CardScheme.UNKNOWN, true, provideFailure(ValidationError.CVV_INVALID_CARD_SCHEME)),
            Arguments.of("1234", CardScheme.UNKNOWN, true, provideFailure(ValidationError.CVV_INVALID_CARD_SCHEME)),
        )

        private fun provideFailure(errorCode: String) = ValidationResult.Failure(ValidationError(errorCode))

        private fun provideSuccess() = ValidationResult.Success(Unit)
    }
}
