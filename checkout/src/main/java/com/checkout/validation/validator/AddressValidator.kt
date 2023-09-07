package com.checkout.validation.validator

import com.checkout.base.util.ADDRESS_LINE1_LENGTH
import com.checkout.base.util.ADDRESS_LINE2_LENGTH
import com.checkout.base.util.CITY_LENGTH
import com.checkout.base.util.STATE_LENGTH
import com.checkout.base.util.ZIP_LENGTH
import com.checkout.tokenization.mapper.request.AddressValidationRequestToAddressDataMapper
import com.checkout.tokenization.model.Address
import com.checkout.validation.error.ValidationError
import com.checkout.validation.model.AddressValidationRequest
import com.checkout.validation.model.ValidationResult
import com.checkout.validation.validator.contract.Validator

/**
 * Class used to validate address.
 */
internal class AddressValidator : Validator<AddressValidationRequest, Address> {

    /**
     * Checks the given [Address].
     *
     * @param data - The address provided for validation of type [AddressValidationRequest].
     * @return [ValidationResult.Success] response when address is valid or [ValidationResult.Failure].
     */
    override fun validate(data: AddressValidationRequest): ValidationResult<Address> = try {
        val address = AddressValidationRequestToAddressDataMapper().map(data)

        validateAddress(address)

        ValidationResult.Success(address)
    } catch (e: ValidationError) {
        ValidationResult.Failure(e)
    }

    /**
     * Checks if the given [address] is supported.
     *
     * @throws [ValidationError] if [address] object's fields' length
     * validations are not satisfied from the API requirements.
     *
     * See [API Reference](https://api-reference.checkout.com/#operation/requestAToken).
     */
    @Throws(ValidationError::class)
    private fun validateAddress(address: Address) {
        when {
            address.addressLine1.length > ADDRESS_LINE1_LENGTH -> throw ValidationError(
                ValidationError.ADDRESS_LINE1_INCORRECT_LENGTH,
                "Address line 1 exceeding minimum length of characters"
            )
            address.addressLine2.length > ADDRESS_LINE2_LENGTH -> throw ValidationError(
                ValidationError.ADDRESS_LINE2_INCORRECT_LENGTH,
                "Address line 2 exceeding minimum length of characters"
            )
            address.city.length > CITY_LENGTH -> throw ValidationError(
                ValidationError.INVALID_CITY_LENGTH,
                "City exceeding minimum length of characters"
            )
            address.state.length > STATE_LENGTH -> throw ValidationError(
                ValidationError.INVALID_STATE_LENGTH,
                "State exceeding minimum length of characters"
            )
            address.zip.length > ZIP_LENGTH -> throw ValidationError(
                ValidationError.INVALID_ZIP_LENGTH,
                "Zipcode exceeding minimum length of characters"
            )
        }
    }
}
