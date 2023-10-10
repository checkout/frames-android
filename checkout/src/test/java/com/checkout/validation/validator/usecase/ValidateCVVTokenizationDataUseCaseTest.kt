package com.checkout.validation.validator.usecase

import com.checkout.base.error.CheckoutError
import com.checkout.base.model.CardScheme
import com.checkout.base.usecase.UseCase
import com.checkout.tokenization.model.ValidateCVVTokenizationRequest
import com.checkout.tokenization.usecase.ValidateCVVTokenizationDataUseCase
import com.checkout.validation.api.CVVComponentValidator
import com.checkout.validation.error.ValidationError
import com.checkout.validation.model.ValidationResult
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class ValidateCVVTokenizationDataUseCaseTest {

    @RelaxedMockK
    lateinit var mockCVVComponentValidator: CVVComponentValidator

    private lateinit var validateCVVTokenizationDataUseCase:
            UseCase<ValidateCVVTokenizationRequest, ValidationResult<Unit>>

    @BeforeEach
    fun setUp() {
        validateCVVTokenizationDataUseCase =
            ValidateCVVTokenizationDataUseCase(cvvComponentValidator = mockCVVComponentValidator)
        every { mockCVVComponentValidator.validate(any(), any()) } returns ValidationResult.Success(Unit)
    }

    @Test
    fun `when invalid cvv is requested then return failure result`() {
        // Given
        val mockRequest = mockk<ValidateCVVTokenizationRequest>()
        val cvv = "???"
        val cardScheme = CardScheme.VISA
        val expectedResult = ValidationResult.Failure(
            CheckoutError(
                errorCode = ValidationError.CVV_INVALID_LENGTH, message = "Please enter a valid security code"
            )
        )

        every { mockRequest.cvv } returns cvv
        every { mockRequest.cardScheme } returns cardScheme

        every {
            mockCVVComponentValidator.validate(
                eq(cvv), eq(cardScheme)
            )
        } returns ValidationResult.Failure(
            CheckoutError(
                errorCode = ValidationError.CVV_INVALID_LENGTH, message = "Please enter a valid security code"
            )
        )

        // When
        val result = validateCVVTokenizationDataUseCase.execute(mockRequest)

        // Then
        assertTrue(result is ValidationResult.Failure)
        val errorValidationResult = (result as? ValidationResult.Failure)
        assertEquals(expectedResult.error.errorCode, errorValidationResult?.error?.errorCode)
        assertEquals(expectedResult.error.message, errorValidationResult?.error?.message)
    }

    @Test
    fun `when valid cvv is requested then return success result`() {
        // Given
        val mockRequest = mockk<ValidateCVVTokenizationRequest>()
        val cvv = "123"
        val cardScheme = CardScheme.VISA
        val expectedResult = ValidationResult.Success(Unit)

        every { mockRequest.cvv } returns cvv
        every { mockRequest.cardScheme } returns cardScheme

        every {
            mockCVVComponentValidator.validate(
                eq(cvv), eq(cardScheme)
            )
        } returns ValidationResult.Success(Unit)

        // When
        val result = validateCVVTokenizationDataUseCase.execute(mockRequest)

        // Then
        assertTrue(result is ValidationResult.Success)
        assertEquals(expectedResult.value, (result as? ValidationResult.Success)?.value)
    }
}
