package com.checkout.logging.utils

import com.checkout.base.model.Environment

internal fun Environment.toLoggingEnvironment() = when (this) {
    Environment.PRODUCTION -> com.checkout.eventlogger.Environment.PRODUCTION
    Environment.SANDBOX -> com.checkout.eventlogger.Environment.SANDBOX
}

internal fun Environment.toLoggingName() = when (this) {
    Environment.PRODUCTION -> "production"
    Environment.SANDBOX -> "sandbox"
}
