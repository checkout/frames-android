package com.checkout.validation.validator

import android.annotation.SuppressLint
import com.checkout.tokenization.model.Address
import com.checkout.base.model.Country
import com.checkout.validation.error.ValidationError
import com.checkout.validation.model.AddressValidationRequest
import com.checkout.validation.model.ValidationResult
import com.checkout.validation.validator.contract.Validator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@SuppressLint("NewApi")
internal class AddressValidatorTest {

    private lateinit var addressValidator: Validator<AddressValidationRequest, Address>

    @BeforeEach
    fun setUp() {
        addressValidator = AddressValidator()
    }

    @ParameterizedTest
    @MethodSource(
        "invalidAddressArguments",
        "validAddressArguments",
    )
    fun `Validation of given address returns correct validation result`(
        testAddressLine1: String,
        testAddressLine2: String,
        testCity: String,
        testState: String,
        testZip: String,
        testCountry: Country,
        expectedResult: ValidationResult<Address>
    ) {
        // Given
        val addressValidationRequest = AddressValidationRequest(
            testAddressLine1,
            testAddressLine2,
            testCity,
            testState,
            testZip,
            testCountry
        )

        // When
        val result = addressValidator.validate(addressValidationRequest)

        // Then
        when (expectedResult) {
            is ValidationResult.Success -> {
                val resultScheme = (result as? ValidationResult.Success<Address>)?.value
                Assertions.assertEquals(expectedResult.value, resultScheme)
            }
            is ValidationResult.Failure -> Assertions.assertEquals(
                expectedResult.error.errorCode,
                (result as? ValidationResult.Failure)?.error?.errorCode
            )
        }
    }

    companion object {
        @JvmStatic
        fun invalidAddressArguments(): Stream<Arguments> = Stream.of(
            Arguments.of(
                "26  Eaton Road,Northampton,England, Lucas Green Nurseries, Lucas Green, West End, " +
                        "Flat 2, 21 Harbour Road, Rushed\",NN10 0FS, 11 Headland Close, Portland,Flat 15," +
                        " Milled Lodge, 275 Wake Green Road, Birmingham",
                "Lucas Green Nurseries, Lucas Green",
                "City of London",
                "England",
                "SE1P",
                Country.from("GB"),
                provideFailure(ValidationError.ADDRESS_LINE1_INCORRECT_LENGTH)
            ),

            Arguments.of(
                "26  Eaton Road",
                "26  Eaton Road,Northampton,England, Lucas Green Nurseries," +
                        " Lucas Green, West End, Flat 2, 21 Harbour Road, Rushed\",NN10 0FS, " +
                        "11 Headland Close, Portland,Flat 15, Milled Lodge, 275 Wake Green Road, Birmingham",
                "City of London",
                "England",
                "SE1P",
                Country.from("GB"),
                provideFailure(ValidationError.ADDRESS_LINE2_INCORRECT_LENGTH)
            ),

            Arguments.of(
                "30  Euston Road",
                "26  Eaton Road,Northampton,England, Lucas Green Nurseries",
                "Newport (Welsh: Canned), City of London, Manchester",
                "England",
                "SE1P",
                Country.from("GB"),
                provideFailure(ValidationError.INVALID_CITY_LENGTH)
            ),

            Arguments.of(
                "30  Euston Road",
                "26  Eaton Road,Northampton,England, Lucas Green Nurseries",
                "City of London",
                "Northern Ireland,Scotland, England and dummy state entries",
                "SE1P",
                Country.from("GB"),
                provideFailure(ValidationError.INVALID_STATE_LENGTH)
            ),

            Arguments.of(
                "30  Euston Road",
                "26  Eaton Road,Northampton,England, Lucas Green Nurseries",
                "City of London",
                "Northern Ireland",
                "SW1W 0NY, EC1–EC4, NW1W, SE1P, SW1 fake entries zip code",
                Country.from("GB"),
                provideFailure(ValidationError.INVALID_ZIP_LENGTH)
            )
        )

        @JvmStatic
        fun validAddressArguments(): Stream<Arguments> = Stream.of(
            Arguments.of(
                "40, Silverfish road",
                "Thornton heath",
                "Cordon",
                "England",
                "CR7 6DU",
                Country.from("GB"),
                provideSuccess(
                    Address(
                        "40, Silverfish road",
                        "Thornton heath",
                        "Cordon",
                        "England",
                        "CR7 6DU",
                        Country.from("GB")
                    )
                )
            )
        )

        private fun provideFailure(errorCode: String) = ValidationResult.Failure(ValidationError(errorCode))

        private fun provideSuccess(address: Address) = ValidationResult.Success(address)
    }
}
