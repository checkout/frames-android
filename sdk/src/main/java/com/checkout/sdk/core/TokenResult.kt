package com.checkout.sdk.core

import com.checkout.sdk.response.CardTokenisationFail
import com.checkout.sdk.response.CardTokenizationResponse

sealed class TokenResult {

    class TokenResultSuccess(val response: CardTokenizationResponse) : TokenResult()

    class TokenResultTokenisationFail(val error: CardTokenisationFail) : TokenResult()

    class TokenResultNetworkError(val exception: Exception) : TokenResult()

}
