package com.checkout.android_sdk.network

import android.os.Handler
import com.checkout.android_sdk.CheckoutAPIClient
import com.checkout.android_sdk.FramesLogger
import com.checkout.android_sdk.Response.GooglePayTokenisationFail
import com.checkout.android_sdk.Response.GooglePayTokenisationResponse
import com.checkout.android_sdk.network.TokenisationResult.*

internal class InternalGooglePayTokenGeneratedListener(
    private val listener: CheckoutAPIClient.OnGooglePayTokenGenerated,
    private val responseHandler: Handler,
    logger: FramesLogger,
) : TokenisationRequestListener<GooglePayTokenisationResponse> {

    private val loggingListener = TokenisationRequestLoggingListener<GooglePayTokenisationResponse>(logger)

    override fun onTokenRequestComplete(response: TokenisationResult<GooglePayTokenisationResponse>) {
        loggingListener.onTokenRequestComplete(response)

        responseHandler.post {
            when (response) {
                is Success -> listener.onTokenGenerated(response.result)
                is Fail -> listener.onError(response.error as GooglePayTokenisationFail)
                is Error -> listener.onNetworkError(response.networkError)
            }
        }
    }
}