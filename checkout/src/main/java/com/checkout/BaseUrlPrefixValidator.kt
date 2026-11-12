package com.checkout

internal object BaseUrlPrefixValidator {
    fun validPrefixOrNull(baseUrlPrefix: String?): String? =
        baseUrlPrefix?.takeIf { LABEL_REGEX.matches(it) }

    private val LABEL_REGEX = Regex("^[a-zA-Z0-9]([a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?$")
}
