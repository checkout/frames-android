package com.checkout.sdk.response

/**
 * The response model object for the Google Pay tokenisation response
 */
class GooglePayTokenizationResponse(
    val type: String? = null,
    private val token: String,
    val expiration: String? = null
) : TokenResponse {

    override fun token(): String {
        return token
    }
}
