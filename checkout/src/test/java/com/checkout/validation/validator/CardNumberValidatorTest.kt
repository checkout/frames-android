package com.checkout.validation.validator

import android.annotation.SuppressLint
import com.checkout.base.model.CardScheme
import com.checkout.validation.error.ValidationError
import com.checkout.validation.model.CardNumberValidationRequest
import com.checkout.validation.model.ValidationResult
import com.checkout.validation.validator.contract.Checker
import com.checkout.validation.validator.contract.Validator
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class CardNumberValidatorTest {

    @RelaxedMockK
    lateinit var mockLuhnChecker: Checker<String>

    private lateinit var cardNumberValidator: Validator<CardNumberValidationRequest, CardScheme>

    @BeforeEach
    internal fun setUp() {
        cardNumberValidator = CardNumberValidator(mockLuhnChecker)

        every { mockLuhnChecker.check(any()) } returns true
    }

    @Test
    fun `Validation of card number with unknown card scheme returns correct error`() {
        // Given
        val request = CardNumberValidationRequest("11111111111111111")
        val expected = ValidationError.CARD_NUMBER_SCHEME_UNRECOGNIZED

        // When
        val result = cardNumberValidator.validate(request) as? ValidationResult.Failure

        // Then
        assertEquals(expected, result?.error?.errorCode)
    }

    @Test
    fun `Validation of card number with known card scheme invokes Luhn check`() {
        // Given
        val cardNumber = "4917610000000000003"
        val request = CardNumberValidationRequest(cardNumber) // valid VISA card number
        val expected = CardScheme.VISA

        // When
        val result = cardNumberValidator.validate(request) as? ValidationResult.Success<CardScheme>

        // Then
        assertEquals(expected, result?.value)
        verify { mockLuhnChecker.check(cardNumber) }
    }

    @Test
    fun `Validation of card number with failed Luhn check returns correct error`() {
        // Given
        val request = CardNumberValidationRequest("4917610000000000003") // valid VISA card number
        val expected = ValidationError.CARD_NUMBER_LUHN_CHECK_ERROR
        every { mockLuhnChecker.check(any()) } returns false

        // When
        val result = cardNumberValidator.validate(request) as? ValidationResult.Failure

        // Then
        assertEquals(expected, result?.error?.errorCode)
    }

    @ParameterizedTest(
        name = "Expected validation result {2} received " + "when card number {0} validation (isEager = {1}) requested"
    )
    @MethodSource(
        "validationSuccessArguments",
        "validationFailureArguments",
        "eagerValidationSuccessArguments",
        "eagerValidationFailureArguments"
    )
    fun `Validation of given card number returns correct validation result`(
        cardNumber: String,
        isEager: Boolean,
        expectedResult: ValidationResult<CardScheme>
    ) {
        // Given
        val request = CardNumberValidationRequest(cardNumber, isEager)

        // When
        val result = cardNumberValidator.validate(request)

        // Then
        when (expectedResult) {
            is ValidationResult.Success -> {
                val resultScheme = (result as? ValidationResult.Success<CardScheme>)?.value
                assertEquals(expectedResult.value, resultScheme)
            }
            is ValidationResult.Failure -> assertEquals(
                expectedResult.error.errorCode,
                (result as? ValidationResult.Failure)?.error?.errorCode
            )
        }
    }

    companion object {
        @JvmStatic
        fun validationSuccessArguments(): Stream<Arguments> = Stream.of(
            Arguments.of("378282246310005", false, provideSuccess(CardScheme.AMERICAN_EXPRESS)),
            Arguments.of("345678901234564", false, provideSuccess(CardScheme.AMERICAN_EXPRESS)),
            Arguments.of("30569309025904", false, provideSuccess(CardScheme.DINERS_CLUB)),
            Arguments.of("38520000023237", false, provideSuccess(CardScheme.DINERS_CLUB)),
            Arguments.of("30123456789019", false, provideSuccess(CardScheme.DINERS_CLUB)),
            Arguments.of("6011039964691945", false, provideSuccess(CardScheme.DISCOVER)),
            Arguments.of("6441111111111117", false, provideSuccess(CardScheme.DISCOVER)),
            Arguments.of("6501111111111117", false, provideSuccess(CardScheme.DISCOVER)),
            Arguments.of("3530111333300000", false, provideSuccess(CardScheme.JCB)),
            Arguments.of("5297412542005689", false, provideSuccess(CardScheme.MADA)),
            Arguments.of("6759649826438453", false, provideSuccess(CardScheme.MAESTRO)),
            Arguments.of("6016607095058666", false, provideSuccess(CardScheme.MAESTRO)),
            Arguments.of("501800000009", false, provideSuccess(CardScheme.MAESTRO)),
            Arguments.of("5000550000000029", false, provideSuccess(CardScheme.MAESTRO)),
            Arguments.of("6799990100000000019", false, provideSuccess(CardScheme.MAESTRO)),
            Arguments.of("6011111111111111117", false, provideSuccess(CardScheme.MAESTRO)),
            Arguments.of("5555555555554444", false, provideSuccess(CardScheme.MASTERCARD)),
            Arguments.of("2223000048400011", false, provideSuccess(CardScheme.MASTERCARD)),
            Arguments.of("2234888888888882", false, provideSuccess(CardScheme.MASTERCARD)),
            Arguments.of("2512777777777772", false, provideSuccess(CardScheme.MASTERCARD)),
            Arguments.of("2705555555555553", false, provideSuccess(CardScheme.MASTERCARD)),
            Arguments.of("2720333333333334", false, provideSuccess(CardScheme.MASTERCARD)),
            Arguments.of("5200828282828210", false, provideSuccess(CardScheme.MASTERCARD)),
            Arguments.of("5105105105105100", false, provideSuccess(CardScheme.MASTERCARD)),
            Arguments.of("5436031030606378", false, provideSuccess(CardScheme.MASTERCARD)),
            Arguments.of("5199992312641465", false, provideSuccess(CardScheme.MASTERCARD)),
            Arguments.of("2223000010479399", false, provideSuccess(CardScheme.MASTERCARD)),
            Arguments.of("5122991234567891", false, provideSuccess(CardScheme.MASTERCARD)),
            Arguments.of("5125761234567895", false, provideSuccess(CardScheme.MASTERCARD)),
            Arguments.of("4242424242424242", false, provideSuccess(CardScheme.VISA)),
            Arguments.of("4000056655665556", false, provideSuccess(CardScheme.VISA)),
            Arguments.of("4917610000000000003", false, provideSuccess(CardScheme.VISA)),
            Arguments.of("4000056655665", false, provideSuccess(CardScheme.VISA)),
            Arguments.of("4543474002249996", false, provideSuccess(CardScheme.VISA)),
            Arguments.of("4002931234567895", false, provideSuccess(CardScheme.VISA)),
            Arguments.of("4029791234567892", false, provideSuccess(CardScheme.VISA)),
            Arguments.of("4658584090000001", false, provideSuccess(CardScheme.VISA)),
            Arguments.of("4484070000035519", false, provideSuccess(CardScheme.VISA)),
            Arguments.of("4000144433439906", false, provideSuccess(CardScheme.VISA)),
            Arguments.of("4000144433439906424", false, provideSuccess(CardScheme.VISA)),
            // With whitespaces
            Arguments.of("37828 22463 10005", false, provideSuccess(CardScheme.AMERICAN_EXPRESS)),
            Arguments.of("37828\n22463\n10005", false, provideSuccess(CardScheme.AMERICAN_EXPRESS)),
            Arguments.of("\t37828\t22463\t10005", false, provideSuccess(CardScheme.AMERICAN_EXPRESS))
        )

        @JvmStatic
        fun validationFailureArguments(): Stream<Arguments> = Stream.of(
            Arguments.of("3782822?", false, provideFailure(ValidationError.CARD_NUMBER_INVALID_CHARACTERS)),
            Arguments.of("3dfgfdg", false, provideFailure(ValidationError.CARD_NUMBER_INVALID_CHARACTERS)),
            Arguments.of("6011039964691945ex", false, provideFailure(ValidationError.CARD_NUMBER_INVALID_CHARACTERS)),
            Arguments.of("q3530111333300000", false, provideFailure(ValidationError.CARD_NUMBER_INVALID_CHARACTERS)),
            Arguments.of("529741qwe2542005689", false, provideFailure(ValidationError.CARD_NUMBER_INVALID_CHARACTERS)),
            Arguments.of("6016607_ 0950586", false, provideFailure(ValidationError.CARD_NUMBER_INVALID_CHARACTERS)),
            Arguments.of("223488 888888-8882", false, provideFailure(ValidationError.CARD_NUMBER_INVALID_CHARACTERS)),
            Arguments.of("2720333333333.", false, provideFailure(ValidationError.CARD_NUMBER_INVALID_CHARACTERS))
        )

        @JvmStatic
        fun eagerValidationSuccessArguments(): Stream<Arguments> = Stream.of(
            Arguments.of("34", true, provideSuccess(CardScheme.AMERICAN_EXPRESS)),
            Arguments.of("37828", true, provideSuccess(CardScheme.AMERICAN_EXPRESS)),
            Arguments.of("30", true, provideSuccess(CardScheme.DINERS_CLUB)),
            Arguments.of("385200", true, provideSuccess(CardScheme.DINERS_CLUB)),
            Arguments.of("601103", true, provideSuccess(CardScheme.DISCOVER)),
            Arguments.of("644111111111", true, provideSuccess(CardScheme.DISCOVER)),
            Arguments.of("35", true, provideSuccess(CardScheme.JCB)),
            Arguments.of("35301", true, provideSuccess(CardScheme.JCB)),
            Arguments.of("529741", true, provideSuccess(CardScheme.MADA)),
            Arguments.of("5297412", true, provideSuccess(CardScheme.MADA)),
            Arguments.of("67", true, provideSuccess(CardScheme.MAESTRO)),
            Arguments.of("6799990100", true, provideSuccess(CardScheme.MAESTRO)),
            Arguments.of("6011", true, provideSuccess(CardScheme.MAESTRO)),
            Arguments.of("500055", true, provideSuccess(CardScheme.MAESTRO)),
            Arguments.of("55", true, provideSuccess(CardScheme.MASTERCARD)),
            Arguments.of("251277777", true, provideSuccess(CardScheme.MASTERCARD)),
            Arguments.of("42", true, provideSuccess(CardScheme.VISA)),
            Arguments.of("424", true, provideSuccess(CardScheme.VISA)),
            Arguments.of("4242", true, provideSuccess(CardScheme.VISA)),
            Arguments.of("42424", true, provideSuccess(CardScheme.VISA)),
            Arguments.of("424242", true, provideSuccess(CardScheme.VISA)),
            Arguments.of("4242424", true, provideSuccess(CardScheme.VISA)),
            Arguments.of("42424242", true, provideSuccess(CardScheme.VISA)),
            Arguments.of("424242424", true, provideSuccess(CardScheme.VISA)),
            Arguments.of("4242424242", true, provideSuccess(CardScheme.VISA)),
            Arguments.of("42424242424", true, provideSuccess(CardScheme.VISA)),
            Arguments.of("424242424242", true, provideSuccess(CardScheme.VISA)),
            Arguments.of("4242424242424", true, provideSuccess(CardScheme.VISA)),
            Arguments.of("42424242424242", true, provideSuccess(CardScheme.VISA)),
            Arguments.of("424242424242424", true, provideSuccess(CardScheme.VISA)),
            Arguments.of("4242424242424242", true, provideSuccess(CardScheme.VISA)),
            // With whitespaces
            Arguments.of("37828 22", true, provideSuccess(CardScheme.AMERICAN_EXPRESS)),
            Arguments.of("37828\n22463\n10", true, provideSuccess(CardScheme.AMERICAN_EXPRESS)),
            Arguments.of("\t37828\t2246", true, provideSuccess(CardScheme.AMERICAN_EXPRESS))
        )

        @JvmStatic
        fun eagerValidationFailureArguments(): Stream<Arguments> = Stream.of(
            Arguments.of("3782822?", true, provideFailure(ValidationError.CARD_NUMBER_INVALID_CHARACTERS)),
            Arguments.of("3dfgfdg", true, provideFailure(ValidationError.CARD_NUMBER_INVALID_CHARACTERS)),
            Arguments.of("6011039964691945ex", true, provideFailure(ValidationError.CARD_NUMBER_INVALID_CHARACTERS)),
            Arguments.of("q3530111333300", true, provideFailure(ValidationError.CARD_NUMBER_INVALID_CHARACTERS)),
            Arguments.of("529741qwe2542005689", true, provideFailure(ValidationError.CARD_NUMBER_INVALID_CHARACTERS)),
            Arguments.of("6016607_ 0950586", true, provideFailure(ValidationError.CARD_NUMBER_INVALID_CHARACTERS)),
            Arguments.of("223488 888888-8882", true, provideFailure(ValidationError.CARD_NUMBER_INVALID_CHARACTERS)),
            Arguments.of("272333333.", true, provideFailure(ValidationError.CARD_NUMBER_INVALID_CHARACTERS))
        )

        private fun provideFailure(errorCode: String) = ValidationResult.Failure(ValidationError(errorCode))

        private fun provideSuccess(cardScheme: CardScheme) = ValidationResult.Success(cardScheme)
    }
}
