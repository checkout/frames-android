package com.checkout.validation.validator.usecase

import com.checkout.base.error.CheckoutError
import com.checkout.base.model.CardScheme
import com.checkout.base.usecase.UseCase
import com.checkout.mock.CardTokenTestData
import com.checkout.tokenization.mapper.request.AddressToAddressValidationRequestDataMapper
import com.checkout.tokenization.model.Address
import com.checkout.tokenization.model.Card
import com.checkout.tokenization.model.ExpiryDate
import com.checkout.tokenization.model.Phone
import com.checkout.tokenization.usecase.ValidateTokenizationDataUseCase
import com.checkout.validation.api.CardValidator
import com.checkout.validation.model.AddressValidationRequest
import com.checkout.validation.model.PhoneValidationRequest
import com.checkout.validation.model.ValidationResult
import com.checkout.validation.validator.contract.Validator
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class ValidateTokenizationDataUseCaseTest {

    @RelaxedMockK
    lateinit var mockCardValidator: CardValidator

    @RelaxedMockK
    lateinit var mockAddressValidator: Validator<AddressValidationRequest, Address>

    @RelaxedMockK
    lateinit var mockPhoneValidator: Validator<PhoneValidationRequest, Phone>

    @RelaxedMockK
    private lateinit var addressToAddressValidationRequestDataMapper: AddressToAddressValidationRequestDataMapper

    private lateinit var validateTokenizationDataUseCase: UseCase<Card, ValidationResult<Unit>>

    @BeforeEach
    fun setUp() {
        validateTokenizationDataUseCase = ValidateTokenizationDataUseCase(
            mockCardValidator,
            mockAddressValidator,
            mockPhoneValidator,
            addressToAddressValidationRequestDataMapper
        )

        every { mockCardValidator.validateCardNumber(any()) } returns ValidationResult.Success(CardScheme.VISA)
        every { mockCardValidator.validateCvv(any(), any()) } returns ValidationResult.Success(Unit)

        every {
            mockCardValidator.validateExpiryDate(
                any<String>(),
                any()
            )
        } returns ValidationResult.Success(ExpiryDate(20, 25))

        every {
            mockCardValidator.validateExpiryDate(
                any<Int>(),
                any()
            )
        } returns ValidationResult.Success(ExpiryDate(20, 25))

        every {
            addressToAddressValidationRequestDataMapper.map(any())
        } returns CardTokenTestData.addressValidationRequest

        every { mockAddressValidator.validate(any()) } returns ValidationResult.Success(CardTokenTestData.address)
        every { mockPhoneValidator.validate(any()) } returns ValidationResult.Success(CardTokenTestData.phone)
    }

    @Test
    fun `when valid card data is requested then return success result`() {
        // Given
        val mockRequest = CardTokenTestData.card
        val captureAddress = slot<AddressValidationRequest>()
        val capturePhone = slot<PhoneValidationRequest>()

        // When
        val result = validateTokenizationDataUseCase.execute(mockRequest)

        // Then
        verify { mockCardValidator.validateCardNumber(eq(mockRequest.number)) }
        verify { mockCardValidator.validateCvv(eq(mockRequest.cvv ?: ""), eq(CardScheme.VISA)) }
        verify {
            mockCardValidator.validateExpiryDate(
                eq(mockRequest.expiryDate.expiryMonth),
                eq(mockRequest.expiryDate.expiryYear)
            )
        }
        mockRequest.billingAddress?.let { verify { mockAddressValidator.validate(capture(captureAddress)) } }
        mockRequest.phone?.let { verify { mockPhoneValidator.validate(capture(capturePhone)) } }

        assertEquals(mockRequest.billingAddress?.addressLine1, captureAddress.captured.addressLine1)
        assertEquals(mockRequest.billingAddress?.addressLine2, captureAddress.captured.addressLine2)
        assertEquals(mockRequest.billingAddress?.state, captureAddress.captured.state)
        assertEquals(mockRequest.billingAddress?.city, captureAddress.captured.city)
        assertEquals(mockRequest.billingAddress?.zip, captureAddress.captured.zip)
        assertEquals(mockRequest.billingAddress?.country?.iso3166Alpha2, captureAddress.captured.country.iso3166Alpha2)
        assertEquals(mockRequest.billingAddress?.country?.dialingCode, captureAddress.captured.country.dialingCode)

        assertEquals(mockRequest.phone?.number, capturePhone.captured.number)
        assertEquals(mockRequest.phone?.country, capturePhone.captured.country)
        assertTrue(result is ValidationResult.Success)
    }

    @Test
    fun `when invalid card number is requested then return failure result`() {
        // Given
        val mockRequest = mockk<Card>()
        val mockNumber = "123123"
        val expectedResult = ValidationResult.Failure(CheckoutError("123"))

        every { mockRequest.number } returns mockNumber

        every { mockCardValidator.validateCardNumber(eq(mockNumber)) } returns
                ValidationResult.Failure(CheckoutError("123"))

        // When
        val result = validateTokenizationDataUseCase.execute(mockRequest)

        // Then
        assertTrue(result is ValidationResult.Failure)
        assertEquals(expectedResult.error.errorCode, (result as? ValidationResult.Failure)?.error?.errorCode)
    }

    @Test
    fun `when invalid expiry date is requested then return failure result`() {
        // Given
        val mockRequest = mockk<Card>()
        val expiryMonth = 20
        val expiryYear = 10
        val expectedResult = ValidationResult.Failure(CheckoutError("123"))

        every { mockRequest.number } returns "mockNumber"
        every { mockRequest.expiryDate.expiryMonth } returns expiryMonth
        every { mockRequest.expiryDate.expiryYear } returns expiryYear

        every {
            mockCardValidator.validateExpiryDate(
                eq(expiryMonth),
                eq(expiryYear)
            )
        } returns ValidationResult.Failure(CheckoutError("123"))

        // When
        val result = validateTokenizationDataUseCase.execute(mockRequest)

        // Then
        assertTrue(result is ValidationResult.Failure)
        assertEquals(expectedResult.error.errorCode, (result as? ValidationResult.Failure)?.error?.errorCode)
    }

    @Test
    fun `when invalid card cvv number is requested then return failure result`() {
        // Given
        val mockRequest = mockk<Card>()
        val cvv = "???"
        val cardScheme = CardScheme.VISA
        val expectedResult = ValidationResult.Failure(CheckoutError("123"))

        every { mockRequest.number } returns "mockNumber"
        every { mockRequest.cvv } returns cvv
        every { mockRequest.expiryDate } returns ExpiryDate(10, 25)

        every {
            mockCardValidator.validateCvv(
                eq(cvv),
                eq(cardScheme)
            )
        } returns ValidationResult.Failure(CheckoutError("123"))

        // When
        val result = validateTokenizationDataUseCase.execute(mockRequest)

        // Then
        assertTrue(result is ValidationResult.Failure)
        assertEquals(expectedResult.error.errorCode, (result as? ValidationResult.Failure)?.error?.errorCode)
    }

    @Test
    fun `when invalid address is requested then return failure result`() {
        // Given
        val mockRequest = mockk<Card>()
        val expectedResult = ValidationResult.Failure(CheckoutError("123"))
        val address = CardTokenTestData.invalidAddress

        every { mockRequest.number } returns "mockNumber"
        every { mockRequest.cvv } returns "cvv"
        every { mockRequest.expiryDate } returns ExpiryDate(10, 25)
        every { mockRequest.billingAddress } returns address

        every {
            mockAddressValidator.validate(addressToAddressValidationRequestDataMapper.map(address))
        } returns ValidationResult.Failure(CheckoutError("123"))

        // When
        val result = validateTokenizationDataUseCase.execute(mockRequest)

        // Then
        assertTrue(result is ValidationResult.Failure)
        assertEquals(expectedResult.error.errorCode, (result as? ValidationResult.Failure)?.error?.errorCode)
    }

    @Test
    fun `when invalid phone is requested then return failure result`() {
        // Given
        val mockRequest = mockk<Card>()
        val expectedResult = ValidationResult.Failure(CheckoutError("123"))
        val phone = CardTokenTestData.inValidPhone

        every { mockRequest.number } returns "mockNumber"
        every { mockRequest.cvv } returns "cvv"
        every { mockRequest.expiryDate } returns ExpiryDate(10, 25)
        every { mockRequest.billingAddress } returns CardTokenTestData.address
        every { mockRequest.phone } returns phone

        every {
            mockPhoneValidator.validate(PhoneValidationRequest(phone.number, phone.country))
        } returns ValidationResult.Failure(CheckoutError("123"))

        // When
        val result = validateTokenizationDataUseCase.execute(mockRequest)

        // Then
        assertTrue(result is ValidationResult.Failure)
        assertEquals(expectedResult.error.errorCode, (result as? ValidationResult.Failure)?.error?.errorCode)
    }
}
