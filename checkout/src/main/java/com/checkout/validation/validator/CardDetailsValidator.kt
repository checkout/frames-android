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
import com.checkout.validation.model.ValidationResult
import com.checkout.validation.validator.contract.Validator

internal class CardDetailsValidator(
    private val expiryDateValidator: Validator<ExpiryDateValidationRequest, ExpiryDate>,
    private val cvvValidator: Validator<CvvValidationRequest, Unit>,
    private val cardNumberValidator: Validator<CardNumberValidationRequest, CardScheme>,
    private val logger: Logger<LoggingEvent>? = null
) : CardValidator {

    init {
        logEvent(ValidationEventType.INITIALIZE)
    }

    override fun validateExpiryDate(
        expiryMonth: String,
        expiryYear: String
    ): ValidationResult<ExpiryDate> {
        logEvent(ValidationEventType.VALIDATE_EXPIRY_DATE_STRING)
        return expiryDateValidator.validate(ExpiryDateValidationRequest(expiryMonth, expiryYear))
    }

    override fun validateExpiryDate(
        expiryMonth: Int,
        expiryYear: Int
    ): ValidationResult<ExpiryDate> {
        logEvent(ValidationEventType.VALIDATE_EXPIRY_DATE_INT)
        return expiryDateValidator.validate(ExpiryDateValidationRequest(expiryMonth.toString(), expiryYear.toString()))
    }

    override fun validateCvv(
        cvv: String,
        cardScheme: CardScheme
    ): ValidationResult<Unit> {
        logEvent(ValidationEventType.VALIDATE_CVV)
        return cvvValidator.validate(CvvValidationRequest(cvv, cardScheme))
    }

    override fun validateCardNumber(cardNumber: String): ValidationResult<CardScheme> {
        logEvent(ValidationEventType.VALIDATE_CARD_NUMBER)
        return cardNumberValidator.validate(CardNumberValidationRequest(cardNumber))
    }

    override fun eagerValidateCardNumber(cardNumber: String): ValidationResult<CardScheme> {
        logEvent(ValidationEventType.VALIDATE_CARD_NUMBER_EAGER)
        return cardNumberValidator.validate(CardNumberValidationRequest(cardNumber, isEagerValidation = true))
    }

    private fun logEvent(eventType: ValidationEventType) =
        logger?.logOnce(LoggingEvent(eventType))
}
