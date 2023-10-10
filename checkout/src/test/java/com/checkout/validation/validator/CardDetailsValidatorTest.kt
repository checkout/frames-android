package com.checkout.validation.validator

import com.checkout.base.model.CardScheme
import com.checkout.logging.Logger
import com.checkout.logging.model.LoggingEvent
import com.checkout.tokenization.model.ExpiryDate
import com.checkout.validation.api.CardValidator
import com.checkout.validation.logging.ValidationEventType
import com.checkout.validation.model.CardNumberValidationRequest
import com.checkout.validation.model.CvvValidationRequest
import com.checkout.validation.model.ExpiryDateValidationRequest
import com.checkout.validation.validator.contract.Validator
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class CardDetailsValidatorTest {

    @RelaxedMockK
    lateinit var mockExpiryDateValidator: Validator<ExpiryDateValidationRequest, ExpiryDate>

    @RelaxedMockK
    lateinit var mockCvvValidator: Validator<CvvValidationRequest, Unit>

    @RelaxedMockK
    lateinit var mockCardNumberValidator: Validator<CardNumberValidationRequest, CardScheme>

    @RelaxedMockK
    lateinit var mockLogger: Logger<LoggingEvent>

    private lateinit var cardValidator: CardValidator
    private var capturedEvent = slot<LoggingEvent>()

    @BeforeEach
    internal fun setup() {
        cardValidator = CardDetailsValidator(
            mockExpiryDateValidator,
            mockCvvValidator,
            mockCardNumberValidator,
            mockLogger,
        )
        every { mockLogger.logOnce(capture(capturedEvent)) } returns Unit
    }

    @Test
    fun `when validateExpiryDate with string data is invoked then validation with correct ExpiryDateSource requested`() {
        // Given
        val mockMonth = "12"
        val mockYear = "19"
        val expected = ExpiryDateValidationRequest("12", "19")

        // When
        cardValidator.validateExpiryDate(mockMonth, mockYear)

        // Then
        verify { mockExpiryDateValidator.validate(eq(expected)) }
    }

    @Test
    fun `when validateExpiryDate with string data is invoked then correct event is logged`() {
        // When
        cardValidator.validateExpiryDate("1", "1")

        // Then
        assertEquals(ValidationEventType.VALIDATE_EXPIRY_DATE_STRING, capturedEvent.captured.eventType)
    }

    @Test
    fun `when validateExpiryDate with integer data is invoked then validation with correct ExpiryDateSource requested`() {
        // Given
        val mockMonth = 2
        val mockYear = 54
        val expected = ExpiryDateValidationRequest("2", "54")

        // When
        cardValidator.validateExpiryDate(mockMonth, mockYear)

        // Then
        verify { mockExpiryDateValidator.validate(eq(expected)) }
    }

    @Test
    fun `when validateExpiryDate with integer data is invoked then correct event is logged`() {
        // When
        cardValidator.validateExpiryDate(1, 1)

        // Then
        assertEquals(ValidationEventType.VALIDATE_EXPIRY_DATE_INT, capturedEvent.captured.eventType)
    }

    @Test
    fun `when validateCvv is invoked then validation with correct CvvValidationRequest requested`() {
        // Given
        val mockCvv = "123"
        val mockCardScheme = CardScheme.JCB
        val expected = CvvValidationRequest("123", CardScheme.JCB)

        // When
        cardValidator.validateCvv(mockCvv, mockCardScheme)

        // Then
        verify { mockCvvValidator.validate(eq(expected)) }
    }

    @Test
    fun `when validateCvv is invoked then correct event is logged`() {
        // When
        cardValidator.validateCvv("234", CardScheme.VISA)

        // Then
        assertEquals(ValidationEventType.VALIDATE_CVV, capturedEvent.captured.eventType)
    }

    @Test
    fun `when validateCardNumber is invoked then validation with correct CardNumberValidationRequest requested`() {
        // Given
        val mockCardNumber = "414141411444414"
        val expected = CardNumberValidationRequest(mockCardNumber)

        // When
        cardValidator.validateCardNumber(mockCardNumber)

        // Then
        verify { mockCardNumberValidator.validate(eq(expected)) }
    }

    @Test
    fun `when validateCardNumber is invoked then correct event is logged`() {
        // When
        cardValidator.validateCardNumber("34535345345345345353")

        // Then
        assertEquals(ValidationEventType.VALIDATE_CARD_NUMBER, capturedEvent.captured.eventType)
    }

    @Test
    fun `when eagerValidateCardNumber is invoked then validation with correct CardNumberValidationRequest requested`() {
        // Given
        val mockCardNumber = "54334534535231"
        val expected = CardNumberValidationRequest(mockCardNumber, true)

        // When
        cardValidator.eagerValidateCardNumber(mockCardNumber)

        // Then
        verify { mockCardNumberValidator.validate(eq(expected)) }
    }

    @Test
    fun `when eagerValidateCardNumber is invoked then correct event is logged`() {
        // When
        cardValidator.eagerValidateCardNumber("3453")

        // Then
        assertEquals(ValidationEventType.VALIDATE_CARD_NUMBER_EAGER, capturedEvent.captured.eventType)
    }
}
