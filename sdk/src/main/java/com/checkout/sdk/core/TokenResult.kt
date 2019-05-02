package com.checkout.sdk.core

import com.checkout.sdk.response.CardTokenizationFail
import com.checkout.sdk.response.CardTokenizationResponse

sealed class TokenResult {

    class TokenResultSuccess(val response: CardTokenizationResponse) : TokenResult()

    class TokenResultTokenisationFail(val error: CardTokenizationFail) : TokenResult()

    class TokenResultNetworkError(val exception: Exception) : TokenResult()

}
