package com.checkout.base.error

/**
 * Base class for all checkout errors
 */
public open class CheckoutError @JvmOverloads constructor(
    public open val errorCode: String,
    message: String? = null,
    throwable: Throwable? = null,
) : Exception(message, throwable)
