package com.checkout.android_sdk.network

import com.checkout.android_sdk.Response.TokenisationResponse

internal interface TokenisationRequestListener<S : TokenisationResponse> {

    fun onTokenRequestComplete(response: TokenisationResult<S>)
}