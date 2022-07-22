package com.checkout.validation.validator.contract

import com.checkout.validation.model.ValidationResult

/**
 * Base validator interface.
 * Used by all validators.
 */
internal interface Validator<D, T : Any> {
    /**
     * Checks the given [data].
     *
     * @param data - The data provided for validation of type [D].
     * @return [ValidationResult.Success] with [T] response when data is valid or [ValidationResult.Failure].
     */
    fun validate(data: D): ValidationResult<T>
}
