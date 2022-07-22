package com.checkout.logging.utils

import com.checkout.base.error.CheckoutError

internal fun HashMap<String, Any>.putErrorAttributes(error: Throwable) {
    (error as? CheckoutError)?.errorCode?.let { this[ERROR_CODE] = it }
    error.message?.let { this[ERROR_MESSAGE] = it }
    this[ERROR_EXCEPTION] = error.stackTraceToString()
}
