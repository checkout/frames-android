package com.checkout

import androidx.annotation.RestrictTo
import com.checkout.base.error.CheckoutError

/**
 * Validates optional merchant-specific subdomain prefixes used by SDK components.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public object BaseUrlPrefixValidator {
    /**
     * Throws [CheckoutError] when a supplied prefix is not a single alphanumeric DNS label.
     */
    @JvmStatic
    public fun validate(baseUrlPrefix: String?) {
        if (
            baseUrlPrefix != null &&
            (baseUrlPrefix.length !in 1..MAX_PREFIX_LENGTH || baseUrlPrefix.any { !it.isAsciiAlphaNumeric() })
        ) {
            throw CheckoutError(
                errorCode = CONFIGURATION_INVALID,
                message = ERROR_MESSAGE_INVALID_BASE_URL_PREFIX,
            )
        }
    }

    private fun Char.isAsciiAlphaNumeric(): Boolean =
        this in 'a'..'z' || this in 'A'..'Z' || this in '0'..'9'

    private const val MAX_PREFIX_LENGTH = 63
    private const val CONFIGURATION_INVALID = "CONFIGURATION_INVALID"
    private const val ERROR_MESSAGE_INVALID_BASE_URL_PREFIX =
        "baseUrlPrefix must contain 1 to 63 ASCII letters or digits."
}
