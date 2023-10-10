package com.checkout.tokenization.usecase

import com.checkout.base.mapper.Mapper
import com.checkout.base.model.CardScheme
import com.checkout.base.usecase.UseCase
import com.checkout.tokenization.model.Address
import com.checkout.tokenization.model.Card
import com.checkout.tokenization.model.Phone
import com.checkout.validation.api.CardValidator
import com.checkout.validation.model.AddressValidationRequest
import com.checkout.validation.model.PhoneValidationRequest
import com.checkout.validation.model.ValidationResult
import com.checkout.validation.validator.contract.Validator

internal class ValidateTokenizationDataUseCase(
    private val cardValidator: CardValidator,
    private val addressValidator: Validator<AddressValidationRequest, Address>,
    private val phoneValidator: Validator<PhoneValidationRequest, Phone>,
    private val addressToAddressValidationRequestDataMapper: Mapper<Address, AddressValidationRequest>,
) : UseCase<Card, ValidationResult<Unit>> {

    override fun execute(data: Card): ValidationResult<Unit> {
        val scheme: CardScheme

        with(cardValidator.validateCardNumber(data.number)) {
            when (this) {
                is ValidationResult.Failure -> return this
                is ValidationResult.Success -> scheme = this.value
            }
        }

        with(cardValidator.validateExpiryDate(data.expiryDate.expiryMonth, data.expiryDate.expiryYear)) {
            if (this is ValidationResult.Failure) return this
        }

        with(cardValidator.validateCvv(data.cvv ?: "", scheme)) {
            if (this is ValidationResult.Failure && !data.cvv.isNullOrEmpty()) return this
        }

        provideAddressValidationRequest(data.billingAddress)?.let {
            with(addressValidator.validate(it)) {
                if (this is ValidationResult.Failure) return this
            }
        }

        providePhoneValidationRequest(data.phone)?.let {
            with(phoneValidator.validate(it)) {
                if (this is ValidationResult.Failure) return this
            }
        }

        return ValidationResult.Success(Unit)
    }

    private fun provideAddressValidationRequest(address: Address?): AddressValidationRequest? = address?.let {
        addressToAddressValidationRequestDataMapper.map(it)
    }

    private fun providePhoneValidationRequest(phone: Phone?): PhoneValidationRequest? =
        phone?.let { PhoneValidationRequest(it.number, it.country) }
}
