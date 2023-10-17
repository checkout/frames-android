package com.checkout.validation.error

import androidx.annotation.RestrictTo
import com.checkout.base.error.CheckoutError

/**
 * Exception used to indicate validation errors
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class ValidationError(
    errorCode: String,
    message: String? = null,
    throwable: Throwable? = null,
) : CheckoutError(errorCode, message, throwable) {

    public companion object {
        /**
         * Generic error for any unexpected errors.
         */
        public const val UNEXPECTED_VALIDATION_ERROR: String = "ValidationError:1000"
        public const val CARD_NUMBER_INVALID_CHARACTERS: String = "ValidationError:1001"
        public const val CARD_NUMBER_LUHN_CHECK_ERROR: String = "ValidationError:1023"
        public const val CARD_NUMBER_SCHEME_UNRECOGNIZED: String = "ValidationError:1024"

        /**
         * Error codes for CVV validation.
         */
        public const val CVV_CONTAINS_NON_DIGITS: String = "ValidationError:1002"
        public const val CVV_INVALID_LENGTH: String = "ValidationError:1003"
        public const val CVV_INCOMPLETE_LENGTH: String = "ValidationError:1022"

        /**
         * Error codes for expiry date validation.
         */
        public const val INVALID_MONTH_STRING: String = "ValidationError:1005"
        public const val INVALID_YEAR_STRING: String = "ValidationError:1006"
        public const val INVALID_MONTH: String = "ValidationError:1007"
        public const val INVALID_YEAR: String = "ValidationError:1008"
        public const val EXPIRY_DATE_IN_PAST: String = "ValidationError:1011"

        /**
         * Error codes for address validation.
         */
        public const val ADDRESS_LINE1_INCORRECT_LENGTH: String = "ValidationError:1012"
        public const val ADDRESS_LINE2_INCORRECT_LENGTH: String = "ValidationError:1013"
        public const val INVALID_CITY_LENGTH: String = "ValidationError:1014"
        public const val INVALID_STATE_LENGTH: String = "ValidationError:1016"
        public const val INVALID_ZIP_LENGTH: String = "ValidationError:1017"

        /**
         * Error codes for phone number validation.
         */
        public const val NUMBER_INCORRECT_LENGTH: String = "ValidationError:1018"
    }
}
