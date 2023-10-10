package com.checkout.validation.validator

import com.checkout.base.error.CheckoutError
import com.checkout.base.model.CardScheme
import com.checkout.validation.api.CVVComponentValidator
import com.checkout.validation.error.ValidationError.Companion.CVV_INVALID_LENGTH
import com.checkout.validation.model.CvvValidationRequest
import com.checkout.validation.model.ValidationResult
import com.checkout.validation.validator.contract.Validator
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class CVVComponentDetailsValidatorTest {

    @RelaxedMockK
    private lateinit var mockCvvValidator: Validator<CvvValidationRequest, Unit>

    private lateinit var cvvComponentValidator: CVVComponentValidator

    @BeforeEach
    internal fun setup() {
        cvvComponentValidator = CVVComponentDetailsValidator(mockCvvValidator)
    }

    @Test
    fun `when validate is invoked then validation with correct CvvValidationRequest requested`() {
        // Given
        val mockCvv = "123"
        val mockCardScheme = CardScheme.JCB
        val expected = CvvValidationRequest("123", CardScheme.JCB)

        // When
        cvvComponentValidator.validate(mockCvv, mockCardScheme)

        // Then
        verify { mockCvvValidator.validate(eq(expected)) }
    }

    @Test
    fun `when validate is invoked with invalid cvv then validation with failure result is returned`() {
        // Given
        val mockCvv = ""
        val mockCardScheme = CardScheme.JCB
        val expectedResult = ValidationResult.Failure(CheckoutError(CVV_INVALID_LENGTH))
        every {
            mockCvvValidator.validate(
                CvvValidationRequest(mockCvv, mockCardScheme),
            )
        } returns ValidationResult.Failure(CheckoutError(CVV_INVALID_LENGTH))

        // When
        val result = cvvComponentValidator.validate(mockCvv, mockCardScheme)

        // Then
        assertEquals(expectedResult.error.errorCode, (result as? ValidationResult.Failure)?.error?.errorCode)
    }
}
