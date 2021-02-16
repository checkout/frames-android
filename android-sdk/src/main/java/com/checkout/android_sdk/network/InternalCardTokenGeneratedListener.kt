package com.checkout.android_sdk.network

import android.os.Handler
import com.checkout.android_sdk.CheckoutAPIClient.OnTokenGenerated
import com.checkout.android_sdk.FramesLogger
import com.checkout.android_sdk.Response.CardTokenisationFail
import com.checkout.android_sdk.Response.CardTokenisationResponse
import com.checkout.android_sdk.network.TokenisationResult.*

internal class InternalCardTokenGeneratedListener(
    private val listener: OnTokenGenerated,
    private val responseHandler: Handler,
    logger: FramesLogger,
) : TokenisationRequestListener<CardTokenisationResponse> {

    private val loggingListener = TokenisationRequestLoggingListener<CardTokenisationResponse>(logger)

    override fun onTokenRequestComplete(response: TokenisationResult<CardTokenisationResponse>) {
        loggingListener.onTokenRequestComplete(response)

        responseHandler.post {
            when (response) {
                is Success -> listener.onTokenGenerated(response.result)
                is Fail -> listener.onError(response.error as CardTokenisationFail)
                is Error -> listener.onNetworkError(response.networkError)
            }
        }
    }
}