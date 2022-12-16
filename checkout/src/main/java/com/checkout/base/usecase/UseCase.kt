package com.checkout.base.usecase

/**
 * Used for creation of use case
 */
public interface UseCase<D, T> {
    /**
     * Execute use case
     *
     * @param data - input data
     * @return Specified data type
     */
    public fun execute(data: D): T
}
