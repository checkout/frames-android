package com.checkout.logging.utils

import com.checkout.base.model.Environment
import com.checkout.base.util.EnvironmentConstants.PRODUCTION_SERVER_URL
import com.checkout.base.util.EnvironmentConstants.SANDBOX_SERVER_URL


internal fun Environment.toBaseUrl(subDomainPrefix: String? = null) = when(this) {
    Environment.PRODUCTION -> subDomainPrefix?.filter { it.isLetterOrDigit() }?.let {
        "https://${subDomainPrefix}.api.checkout.com/tokens"
    } ?: PRODUCTION_SERVER_URL
    Environment.SANDBOX -> subDomainPrefix?.filter { it.isLetterOrDigit() }?.let {
        "https://${subDomainPrefix}.api.sandbox.checkout.com/tokens"
    } ?: SANDBOX_SERVER_URL
}
internal fun Environment.toLoggingEnvironment() = when (this) {
    Environment.PRODUCTION -> com.checkout.eventlogger.Environment.PRODUCTION
    Environment.SANDBOX -> com.checkout.eventlogger.Environment.SANDBOX
}

internal fun Environment.toLoggingName() = when (this) {
    Environment.PRODUCTION -> "production"
    Environment.SANDBOX -> "sandbox"
}
