package com.checkout

import com.checkout.logging.EventLoggerProvider
import com.checkout.validation.api.CardValidator
import com.checkout.validation.validator.CardDetailsValidator
import com.checkout.validation.validator.CardNumberValidator
import com.checkout.validation.validator.CvvValidator
import com.checkout.validation.validator.ExpiryDateValidator
import com.checkout.validation.validator.LuhnChecker

/**
 * Factory class for card validator creation
 */
public object CardValidatorFactory {

    /**
     * Creates public card validator.
     * @return [CardValidator] for individual card details components validation.
     */
    @JvmStatic
    public fun create(): CardValidator {
        return CardDetailsValidator(
            ExpiryDateValidator(),
            CvvValidator(),
            CardNumberValidator(LuhnChecker()),
            EventLoggerProvider.provide()
        )
    }

    /**
     * Creates internal card validator.
     * @return [CardValidator] for individual card details components validation.
     */
    internal fun createInternal(): CardValidator {
        return CardDetailsValidator(
            ExpiryDateValidator(),
            CvvValidator(),
            CardNumberValidator(LuhnChecker())
        )
    }
}
