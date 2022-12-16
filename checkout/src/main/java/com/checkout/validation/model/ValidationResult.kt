package com.checkout.validation.model

import com.checkout.base.error.CheckoutError

/**
 * Class representing a validation result which can be either success or failure.
 */
public sealed class ValidationResult<out S : Any> {
    /**
     * Class representing a successful validation result which may contain a value.
     */
    public class Success<T : Any>(public val value: T) : ValidationResult<T>()

    /**
     * Class representing a failure validation result with an [error] indicating the cause of the failure.
     */
    public class Failure(public val error: CheckoutError) : ValidationResult<Nothing>()
}
