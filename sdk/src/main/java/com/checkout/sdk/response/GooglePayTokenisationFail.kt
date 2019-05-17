package com.checkout.sdk.response

/**
 * The response model object for the Google Pay tokenisation error
 */
data class GooglePayTokenisationFail(
    private val request_id: String?,
    private val error_type: String,
    private val error_codes: List<String>?
) : TokenFail {

    override fun errorCode(): String {
        return error_type
    }

}
