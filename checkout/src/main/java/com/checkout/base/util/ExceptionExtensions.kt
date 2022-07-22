package com.checkout.base.util

import kotlin.coroutines.cancellation.CancellationException

/**
 * Determines if this exception can be caught.
 *
 * There are some exceptions such as [CancellationException] that should not be caught
 * by the application code. These exceptions will be collected here and return false
 * so that they are propagated as expected.
 */
internal val Exception.canBeCaught: Boolean
    get() = when (this) {
        is CancellationException -> false
        else -> true
    }
