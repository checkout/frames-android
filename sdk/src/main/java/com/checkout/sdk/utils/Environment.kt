package com.checkout.sdk.utils

enum class Environment(
    val host: String
) {
    SANDBOX("https://sandbox.checkout.com/api2/"),
    LIVE("https://api2.checkout.com/");

    companion object {
        const val TOKEN_PATH = "v2/tokens/card"
        const val GOOGLE_PAY_PATH = "tokens"
    }
}
