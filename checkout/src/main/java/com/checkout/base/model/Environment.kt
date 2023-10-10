package com.checkout.base.model

import com.checkout.base.util.EnvironmentConstants

public enum class Environment(public val url: String) {
    PRODUCTION(EnvironmentConstants.PRODUCTION_SERVER_URL),
    SANDBOX(EnvironmentConstants.SANDBOX_SERVER_URL),
}
