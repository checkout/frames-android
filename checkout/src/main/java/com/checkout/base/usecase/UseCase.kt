package com.checkout.base.usecase

import androidx.annotation.RestrictTo

/**
 * Used for creation of use case
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public interface UseCase<D, T> {
    /**
     * Execute use case
     *
     * @param data - input data
     * @return Specified data type
     */
    public fun execute(data: D): T
}
