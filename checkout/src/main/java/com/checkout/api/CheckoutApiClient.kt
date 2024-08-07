package com.checkout.api

import com.checkout.api.logging.ApiClientEventType
import com.checkout.logging.EventLoggerProvider
import com.checkout.logging.model.LoggingEvent
import com.checkout.threedsecure.Executor
import com.checkout.threedsecure.model.ThreeDSRequest
import com.checkout.tokenization.model.CVVTokenizationRequest
import com.checkout.tokenization.model.CardTokenRequest
import com.checkout.tokenization.model.GooglePayTokenRequest
import com.checkout.tokenization.repository.TokenRepository

internal class CheckoutApiClient(
    private val tokenRepository: TokenRepository,
    private val threeDSExecutor: Executor<ThreeDSRequest>,
) : CheckoutApiService {

    init {
        val logger = EventLoggerProvider.provide()
        logger.log(LoggingEvent(ApiClientEventType.INITIALIZE))
    }


    override fun createToken(request: CardTokenRequest) = tokenRepository.sendCardTokenRequest(request)

    override fun createToken(request: GooglePayTokenRequest) = tokenRepository.sendGooglePayTokenRequest(request)

    override fun createToken(request: CVVTokenizationRequest) = tokenRepository.sendCVVTokenizationRequest(request)

    override fun handleThreeDS(request: ThreeDSRequest) = threeDSExecutor.execute(request)
}
