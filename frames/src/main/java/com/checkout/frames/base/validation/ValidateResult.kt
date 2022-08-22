package com.checkout.frames.base.validation

/**
 * Class representing a validation result which can be either success or failure.
 */
internal sealed class ValidateResult<out S : Any> {
    /**
     * Class representing a successful validation result which may contain a value.
     */
    class Success<T : Any>(val value: T) : ValidateResult<T>()

    /**
     * Class representing a failure validation result with an [data] indicating the cause of the failure or specific
     * data which required to show in failure result.
     */
    class Failure(val data: String) : ValidateResult<Nothing>()
}
