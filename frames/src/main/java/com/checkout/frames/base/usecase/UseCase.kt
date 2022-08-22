package com.checkout.frames.base.usecase

/**
 * Used for creation of use case
 */
internal interface UseCase<D, T> {
    /**
     * Execute use case
     *
     * @param data - input data
     * @return Specified data type
     */
    fun execute(data: D): T
}
