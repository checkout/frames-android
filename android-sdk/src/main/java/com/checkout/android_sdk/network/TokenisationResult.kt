package com.checkout.android_sdk.network

import com.checkout.android_sdk.Response.TokenisationFail
import com.checkout.android_sdk.Response.TokenisationResponse

sealed class TokenisationResult<S : TokenisationResponse> {

    data class Success<S : TokenisationResponse>(val result: S, val httpStatus: Int) :
        TokenisationResult<S>()

    data class Fail<S : TokenisationResponse>(val error: TokenisationFail, val httpStatus: Int) :
        TokenisationResult<S>()

    data class Error<S : TokenisationResponse>(val networkError: NetworkError) :
        TokenisationResult<S>()
}