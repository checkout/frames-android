package com.checkout.sdk.core

import com.checkout.sdk.response.TokenFail
import com.checkout.sdk.response.TokenResponse

sealed class TokenResult {

    class TokenResultSuccess(val response: TokenResponse) : TokenResult()

    class TokenResultTokenizationFail(val error: TokenFail) : TokenResult()

    class TokenResultNetworkError(val exception: Exception) : TokenResult()

}
