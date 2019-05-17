package com.checkout.sdk.response

/**
 * The response model object for the card tokenisation error
 */
data class CardTokenizationFail(val eventId: String?,
                           val errorCode: String,
                           val message: String?,
                           val errorMessageCodes: List<String>?,
                           val errors: List<String>?) : TokenFail {
    override fun errorCode(): String {
        return errorCode
    }

}
