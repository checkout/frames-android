package com.checkout.validation.validator.contract

/**
 * Base checker interface.
 * Used by all checkers.
 */
internal interface Checker<D> {
    /**
     * Checks the given [data].
     *
     * @param data - The data provided for check of type [D].
     * @return [Boolean] true if check passed, otherwise false
     */
    fun check(data: D): Boolean
}
