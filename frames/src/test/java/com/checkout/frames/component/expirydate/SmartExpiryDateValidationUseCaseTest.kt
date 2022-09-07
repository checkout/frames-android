package com.checkout.frames.component.expirydate

import android.annotation.SuppressLint
import com.checkout.base.error.CheckoutError
import com.checkout.base.usecase.UseCase
import com.checkout.frames.component.expirydate.model.SmartExpiryDateValidationRequest
import com.checkout.frames.component.expirydate.usecase.SmartExpiryDateValidationUseCase
import com.checkout.tokenization.model.ExpiryDate
import com.checkout.validation.api.CardValidator
import com.checkout.validation.model.ValidationResult
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class SmartExpiryDateValidationUseCaseTest {

    @RelaxedMockK
    lateinit var mockCardValidator: CardValidator

    private lateinit var expiryDateValidationUseCase:
            UseCase<SmartExpiryDateValidationRequest, ValidationResult<String>>

    private lateinit var fakeExpiryDate: ExpiryDate

    @BeforeEach
    fun setUp() {
        expiryDateValidationUseCase = SmartExpiryDateValidationUseCase(mockCardValidator)
        fakeExpiryDate = ExpiryDate(12, 12)
    }

    @ParameterizedTest(
        name = "Expected success validation result {0} received for OnFocus when valid {1} expiry date requested"
    )
    @MethodSource("validateSuccessExpiryDateOnFocusBehaviourArguments")
    fun `when valid expiry date is requested then return success result for On Focus behaviour`(
        inputRequest: SmartExpiryDateValidationRequest,
        expectedExpiryDate: ValidationResult<String>,
    ) {
        testExpiryDateValidationResultOnFocusBehaviour(true, inputRequest, expectedExpiryDate)
    }

    @ParameterizedTest(
        name = "Expected failed validation result {0} received for OnFocus\" + \"when invalid {1} expiry date requested"
    )
    @MethodSource("validateInvalidExpiryDateForOnFocusBehaviourArguments")
    fun `when invalid expiry date is requested then return failure result for On Focus behaviour`(
        inputRequest: SmartExpiryDateValidationRequest,
        expectedExpiryDate: ValidationResult<String>,
    ) {
        testExpiryDateValidationResultOnFocusBehaviour(false, inputRequest, expectedExpiryDate)
    }

    @ParameterizedTest(
        name = "Expected success validation result {0} received for OnFocusChanged when valid {1} expiry date requested"
    )
    @MethodSource("validateSuccessExpiryDateOnFocusChangedBehaviourArguments")
    fun `when valid expiry date is requested then return success result for On Focus Changed behaviour`(
        inputRequest: SmartExpiryDateValidationRequest,
        expectedExpiryDate: ValidationResult<String>,
    ) {
        testExpiryDateValidationResultOnFocusChangedBehaviour(true, inputRequest, expectedExpiryDate)
    }

    @ParameterizedTest(
        name = "Expected failed validation result {0} received" +
                " for OnFocusChanged when invalid {1} expiry date requested"
    )
    @MethodSource("validateInvalidExpiryDateOnFocusChangedBehaviourArguments")
    fun `when invalid expiry date is requested then return failure result for On Focus Changed behaviour`(
        inputRequest: SmartExpiryDateValidationRequest,
        expectedExpiryDate: ValidationResult<String>,
    ) {
        testExpiryDateValidationResultOnFocusChangedBehaviour(false, inputRequest, expectedExpiryDate)
    }

    private fun testExpiryDateValidationResultOnFocusBehaviour(
        isVerifyingSuccessResult: Boolean,
        inputRequest: SmartExpiryDateValidationRequest,
        expectedExpiryDate: ValidationResult<String>,
    ) {
        // Given
        every {
            mockCardValidator.validateExpiryDate(any() as String, any() as String)
        } returns
                if (isVerifyingSuccessResult)
                    ValidationResult.Success(fakeExpiryDate)
                else
                    ValidationResult.Failure(CheckoutError("errorCode"))

        // When
        val result = expiryDateValidationUseCase.execute(inputRequest)

        // Then
        when (expectedExpiryDate) {
            is ValidationResult.Success -> {
                Assertions.assertEquals(expectedExpiryDate.value, (result as? ValidationResult.Success<String>)?.value)
            }
            is ValidationResult.Failure -> Assertions.assertEquals(
                expectedExpiryDate.error.message,
                (result as? ValidationResult.Failure)?.error?.message
            )
        }
    }

    private fun testExpiryDateValidationResultOnFocusChangedBehaviour(
        isVerifyingSuccessResult: Boolean,
        inputRequest: SmartExpiryDateValidationRequest,
        expectedExpiryDate: ValidationResult<String>,
    ) {
        // Given
        every {
            mockCardValidator.validateExpiryDate(any() as String, any() as String)
        } returns ValidationResult.Success(fakeExpiryDate)
        // Given
        every {
            mockCardValidator.validateExpiryDate(any() as String, any() as String)
        } returns
                if (isVerifyingSuccessResult)
                    ValidationResult.Success(fakeExpiryDate)
                else
                    ValidationResult.Failure(CheckoutError("errorCode"))

        // When
        val result = expiryDateValidationUseCase.execute(inputRequest)

        // Then
        when (expectedExpiryDate) {
            is ValidationResult.Success -> {
                Assertions.assertEquals(expectedExpiryDate.value, (result as? ValidationResult.Success<String>)?.value)
            }
            is ValidationResult.Failure -> Assertions.assertEquals(
                expectedExpiryDate.error.message,
                (result as? ValidationResult.Failure)?.error?.message
            )
        }
    }

    companion object {
        @JvmStatic
        fun validateSuccessExpiryDateOnFocusBehaviourArguments(): Stream<Arguments> = Stream.of(
            // When field is focussed, validate it as a success while user is typing except expiry date is in the past
            Arguments.of(SmartExpiryDateValidationRequest(true, "1224"), provideSuccess("1224")),
            Arguments.of(SmartExpiryDateValidationRequest(true, "12"), provideSuccess("12")),
            Arguments.of(SmartExpiryDateValidationRequest(true, "122"), provideSuccess("122")),
            Arguments.of(SmartExpiryDateValidationRequest(true, "0112"), provideSuccess("0112")),
            Arguments.of(SmartExpiryDateValidationRequest(true, "1131"), provideSuccess("1131")),

            Arguments.of(SmartExpiryDateValidationRequest(true, "823"), provideSuccess("823")),
            Arguments.of(SmartExpiryDateValidationRequest(true, "555"), provideSuccess("555")),
            Arguments.of(SmartExpiryDateValidationRequest(true, "666"), provideSuccess("666")),

            Arguments.of(SmartExpiryDateValidationRequest(true, "1"), provideSuccess("1")),
            Arguments.of(SmartExpiryDateValidationRequest(true, "2"), provideSuccess("2")),
            Arguments.of(SmartExpiryDateValidationRequest(true, "3"), provideSuccess("3")),
            Arguments.of(SmartExpiryDateValidationRequest(true, "4"), provideSuccess("4")),
            Arguments.of(SmartExpiryDateValidationRequest(true, "5"), provideSuccess("5")),
            Arguments.of(SmartExpiryDateValidationRequest(true, "6"), provideSuccess("6")),
            Arguments.of(SmartExpiryDateValidationRequest(true, "7"), provideSuccess("7")),
            Arguments.of(SmartExpiryDateValidationRequest(true, "8"), provideSuccess("8")),
            Arguments.of(SmartExpiryDateValidationRequest(true, "9"), provideSuccess("9")),
            Arguments.of(SmartExpiryDateValidationRequest(true, "10"), provideSuccess("10")),

            Arguments.of(SmartExpiryDateValidationRequest(true, "1222"), provideSuccess("1222")),
            Arguments.of(SmartExpiryDateValidationRequest(true, "1223"), provideSuccess("1223")),
            Arguments.of(SmartExpiryDateValidationRequest(true, "1290"), provideSuccess("1290")),
            Arguments.of(SmartExpiryDateValidationRequest(true, "1198"), provideSuccess("1198")),
        )

        @JvmStatic
        fun validateSuccessExpiryDateOnFocusChangedBehaviourArguments(): Stream<Arguments> = Stream.of(
            // When onFocusChanged Called
            Arguments.of(SmartExpiryDateValidationRequest(false, "823"), provideSuccess("823")),
            Arguments.of(SmartExpiryDateValidationRequest(false, "555"), provideSuccess("555")),
            Arguments.of(SmartExpiryDateValidationRequest(false, "666"), provideSuccess("666")),

            Arguments.of(SmartExpiryDateValidationRequest(false, "1222"), provideSuccess("1222")),
            Arguments.of(SmartExpiryDateValidationRequest(false, "1223"), provideSuccess("1223")),
            Arguments.of(SmartExpiryDateValidationRequest(false, "1290"), provideSuccess("1290")),
            Arguments.of(SmartExpiryDateValidationRequest(false, "1298"), provideSuccess("1298")),
            Arguments.of(SmartExpiryDateValidationRequest(false, "1198"), provideSuccess("1198")),
            Arguments.of(SmartExpiryDateValidationRequest(false, "1123"), provideSuccess("1123")),
            Arguments.of(SmartExpiryDateValidationRequest(false, "1025"), provideSuccess("1025")),
        )

        @JvmStatic
        fun validateInvalidExpiryDateForOnFocusBehaviourArguments(): Stream<Arguments> = Stream.of(

            // When field is focussed, validate it as a failure when user typed expiry date in the past
            Arguments.of(SmartExpiryDateValidationRequest(true, "111"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "1112"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "1113"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "1114"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "1115"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "1116"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "1117"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "1118"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "1218"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "1011"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "1221"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "921"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "822"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "722"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "212"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "311"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "312"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "1221"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "215"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "921"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "922"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "422"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "322"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "222"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "0122"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "0121"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "0120"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "0119"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "0118"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "0117"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "0116"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "0115"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(true, "0114"), provideFailure()),
        )

        @JvmStatic
        fun validateInvalidExpiryDateOnFocusChangedBehaviourArguments(): Stream<Arguments> = Stream.of(
            // When onFocusChanged Called
            Arguments.of(SmartExpiryDateValidationRequest(false, "11"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "111"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "11"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "1114"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "1115"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "1116"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "1117"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "1118"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "121"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "101"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "122"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "921"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "822"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "722"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "212"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "311"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "312"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "122"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "215"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "921"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "922"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "422"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "322"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "22"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "222"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "0122"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "0121"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "0120"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "112"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "123"), provideFailure()),

            Arguments.of(SmartExpiryDateValidationRequest(false, "11"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "01"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "12"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "2"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "34"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "45"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "56"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "64"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "67"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "78"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "89"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "91"), provideFailure()),
            Arguments.of(SmartExpiryDateValidationRequest(false, "95"), provideFailure())
        )

        private fun provideSuccess(inputExpiryDate: String) = ValidationResult.Success(inputExpiryDate)

        private fun provideFailure() = ValidationResult.Failure(CheckoutError("errorCode"))
    }
}
