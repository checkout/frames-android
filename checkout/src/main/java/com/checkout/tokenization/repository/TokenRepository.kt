package com.checkout.tokenization.repository

import com.checkout.tokenization.model.CardTokenRequest
import com.checkout.tokenization.model.GooglePayTokenRequest

internal interface TokenRepository {

    fun sendCardTokenRequest(cardTokenRequest: CardTokenRequest)

    fun sendGooglePayTokenRequest(googlePayTokenRequest: GooglePayTokenRequest)
}
