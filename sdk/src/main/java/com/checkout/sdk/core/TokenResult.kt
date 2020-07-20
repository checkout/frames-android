package com.checkout.sdk.core

import com.checkout.sdk.response.TokenFail
import com.checkout.sdk.response.TokenResponse

sealed class TokenResult {

    data class TokenResultSuccess(val response: TokenResponse) : TokenResult()

    data class TokenResultTokenizationFail(val error: TokenFail) : TokenResult()

    data class TokenResultNetworkError(val exception: Exception) : TokenResult()

}
