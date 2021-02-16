package com.checkout.android_sdk.network

import com.checkout.android_sdk.FramesLogger
import com.checkout.android_sdk.FramesLogger.Companion.log
import com.checkout.android_sdk.Response.TokenisationResponse

internal class TokenisationRequestLoggingListener<S : TokenisationResponse>(
    private val logger: FramesLogger,
) : TokenisationRequestListener<S> {

    override fun onTokenRequestComplete(response: TokenisationResult<S>) {
        when (response) {
            is TokenisationResult.Success -> {
                log {
                    logger.sendTokenResponseEvent(
                        responseCode = response.httpStatus,
                        successResponse = response.result,
                        failedResponse = null
                    )
                }
            }
            is TokenisationResult.Fail -> {
                log {
                    logger.sendTokenResponseEvent(
                        responseCode = response.httpStatus,
                        successResponse = null,
                        failedResponse = response.error
                    )
                }
            }
            is TokenisationResult.Error -> {
                log {
                    logger.errorEvent(
                        "Tokenisation request failed",
                        response.networkError
                    )
                }
            }
        }

        logger.clear()
    }
}