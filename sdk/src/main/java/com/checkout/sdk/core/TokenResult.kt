package com.checkout.sdk.core

import com.checkout.sdk.response.CardTokenizationFail
import com.checkout.sdk.response.TokenResponse

sealed class TokenResult {

    class TokenResultSuccess(val response: TokenResponse) : TokenResult()

    class TokenResultTokenizationFail(val error: CardTokenizationFail) : TokenResult()

    class TokenResultNetworkError(val exception: Exception) : TokenResult()

}
