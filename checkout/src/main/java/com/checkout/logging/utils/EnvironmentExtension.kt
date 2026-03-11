package com.checkout.logging.utils

import com.checkout.base.model.Environment
import com.checkout.base.util.EnvironmentConstants.PRODUCTION_LOGGING
import com.checkout.base.util.EnvironmentConstants.PRODUCTION_SERVER_URL
import com.checkout.base.util.EnvironmentConstants.SANDBOX_LOGGING
import com.checkout.base.util.EnvironmentConstants.SANDBOX_SERVER_URL
import com.checkout.base.util.HTTPS_PROTOCOL

internal fun Environment.toBaseUrl(baseUrlPrefix: String? = null) = when (this) {
    Environment.PRODUCTION -> PRODUCTION_SERVER_URL.applyPrefix(baseUrlPrefix)
    Environment.SANDBOX -> SANDBOX_SERVER_URL.applyPrefix(baseUrlPrefix)
}

internal fun Environment.toLoggingEnvironment() = when (this) {
    Environment.PRODUCTION -> com.checkout.eventlogger.Environment.PRODUCTION
    Environment.SANDBOX -> com.checkout.eventlogger.Environment.SANDBOX
}

internal fun Environment.toLoggingName() = when (this) {
    Environment.PRODUCTION -> PRODUCTION_LOGGING
    Environment.SANDBOX -> SANDBOX_LOGGING
}

private fun String.applyPrefix(prefix: String?): String {
    val validatedPrefix = prefix?.baseUrlPrefixValidator() ?: return this
    return this.replace(HTTPS_PROTOCOL, "$HTTPS_PROTOCOL$validatedPrefix.")
}

private fun String.baseUrlPrefixValidator() = this
    .takeIf { prefix ->
        prefix.isNotEmpty() && prefix.all { char -> char in 'a'..'z' || char in 'A'..'Z' || char in '0'..'9' }
    }
