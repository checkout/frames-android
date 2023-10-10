package com.checkout.tokenization.repository

import com.checkout.tokenization.model.CVVTokenizationRequest
import com.checkout.tokenization.model.CardTokenRequest
import com.checkout.tokenization.model.GooglePayTokenRequest

internal interface TokenRepository {

    fun sendCardTokenRequest(cardTokenRequest: CardTokenRequest)

    fun sendCVVTokenizationRequest(cvvTokenizationRequest: CVVTokenizationRequest)

    fun sendGooglePayTokenRequest(googlePayTokenRequest: GooglePayTokenRequest)
}
