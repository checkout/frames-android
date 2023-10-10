package com.checkout.validation.validator

import com.checkout.base.util.PHONE_MAX_LENGTH
import com.checkout.base.util.PHONE_MIN_LENGTH
import com.checkout.tokenization.model.Phone
import com.checkout.validation.error.ValidationError
import com.checkout.validation.model.PhoneValidationRequest
import com.checkout.validation.model.ValidationResult
import com.checkout.validation.validator.contract.Validator

/**
 * Class used to validate phone.
 */
internal class PhoneValidator : Validator<PhoneValidationRequest, Phone> {

    /**
     * Checks the given [Phone].
     *
     * @param data - The phone object provided for validation of type [PhoneValidationRequest].
     * @return [ValidationResult.Success] response when phone is valid or [ValidationResult.Failure].
     */
    override fun validate(data: PhoneValidationRequest): ValidationResult<Phone> = try {
        val phone = Phone(
            data.number,
            data.country,
        )
        validatePhone(phone)

        ValidationResult.Success(phone)
    } catch (e: ValidationError) {
        ValidationResult.Failure(e)
    }

    /**
     * Checks if the given [phone] is supported.
     *
     * @throws [ValidationError] if [phone] object's fields' length
     * validations are not satisfied from the API requirements.
     *
     * See [API Reference](https://api-reference.checkout.com/#operation/requestAToken).
     */
    @Throws(ValidationError::class)
    private fun validatePhone(phone: Phone) {
        when {
            phone.number.length < PHONE_MIN_LENGTH || phone.number.length > PHONE_MAX_LENGTH -> throw ValidationError(
                ValidationError.NUMBER_INCORRECT_LENGTH,
                "Invalid length of phone number",
            )
        }
    }
}
