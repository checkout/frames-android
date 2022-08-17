package com.checkout.validation.error

import com.checkout.base.error.CheckoutError
import com.checkout.base.util.canBeCaught

/**
 * Exception used to indicate validation errors
 */
internal class ValidationError(
    errorCode: String,
    message: String? = null,
    throwable: Throwable? = null
) : CheckoutError(errorCode, message, throwable) {

    companion object {
        /**
         * Generic error for any unexpected errors.
         */
        const val UNEXPECTED_VALIDATION_ERROR = "ValidationError:1000"
        const val CARD_NUMBER_INVALID_CHARACTERS = "ValidationError:1001"
        const val CARD_NUMBER_LUHN_CHECK_ERROR = "ValidationError:1023"
        const val CARD_NUMBER_SCHEME_UNRECOGNIZED = "ValidationError:1024"

        /**
         * Error codes for CVV validation.
         */
        const val CVV_CONTAINS_NON_DIGITS = "ValidationError:1002"
        const val CVV_INVALID_LENGTH = "ValidationError:1003"
        const val CVV_INCOMPLETE_LENGTH = "ValidationError:1022"

        /**
         * Error codes for expiry date validation.
         */
        const val INVALID_MONTH_STRING = "ValidationError:1005"
        const val INVALID_YEAR_STRING = "ValidationError:1006"
        const val INVALID_MONTH = "ValidationError:1007"
        const val INVALID_YEAR = "ValidationError:1008"
        const val EXPIRY_DATE_IN_PAST = "ValidationError:1011"

        /**
         * Error codes for address validation.
         */
        const val ADDRESS_LINE1_INCORRECT_LENGTH = "ValidationError:1012"
        const val ADDRESS_LINE2_INCORRECT_LENGTH = "ValidationError:1013"
        const val INVALID_CITY_LENGTH = "ValidationError:1014"
        const val INVALID_COUNTRY = "ValidationError:1015"
        const val INVALID_STATE_LENGTH = "ValidationError:1016"
        const val INVALID_ZIP_LENGTH = "ValidationError:1017"

        /**
         * Error codes for phone number validation.
         */
        const val NUMBER_INCORRECT_LENGTH = "ValidationError:1018"
    }
}

internal fun Exception.asValidationError(
    errorCode: String? = null,
    message: String? = null
): ValidationError = if (canBeCaught) {
    when (this) {
        is ValidationError -> this
        else -> ValidationError(
            errorCode = errorCode ?: ValidationError.UNEXPECTED_VALIDATION_ERROR,
            message = message,
            throwable = this
        )
    }
} else {
    throw this
}
