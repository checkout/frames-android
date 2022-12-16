package com.checkout.validation.logging

import com.checkout.logging.LoggingEventType

internal enum class ValidationEventType(override val eventId: String) : LoggingEventType {
    INITIALIZE("card_validator"),
    VALIDATE_CARD_NUMBER("card_validator_card_number"),
    VALIDATE_CARD_NUMBER_EAGER("card_validator_card_number_eager"),
    VALIDATE_EXPIRY_DATE_STRING("card_validator_expiry_string"),
    VALIDATE_EXPIRY_DATE_INT("card_validator_expiry_integer"),
    VALIDATE_CVV("card_validator_cvv")
}
