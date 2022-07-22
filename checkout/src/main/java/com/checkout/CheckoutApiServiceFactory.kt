package com.checkout

import android.content.Context
import com.checkout.api.CheckoutApiClient
import com.checkout.api.CheckoutApiService
import com.checkout.base.model.Environment
import com.checkout.logging.EventLoggerProvider
import com.checkout.logging.Logger
import com.checkout.logging.model.LoggingEvent
import com.checkout.network.OkHttpProvider
import com.checkout.threedsecure.Executor
import com.checkout.threedsecure.ThreeDSExecutor
import com.checkout.threedsecure.logging.ThreeDSEventLogger
import com.checkout.threedsecure.model.ThreeDSRequest
import com.checkout.threedsecure.usecase.ProcessThreeDSUseCase
import com.checkout.tokenization.TokenNetworkApiClient
import com.checkout.tokenization.mapper.request.AddressToAddressValidationRequestDataMapper
import com.checkout.tokenization.mapper.request.CardToTokenRequestMapper
import com.checkout.tokenization.mapper.response.CardTokenizationNetworkDataMapper
import com.checkout.tokenization.repository.TokenRepository
import com.checkout.tokenization.repository.TokenRepositoryImpl
import com.checkout.tokenization.usecase.ValidateTokenizationDataUseCase
import com.checkout.validation.validator.AddressValidator
import com.checkout.validation.validator.PhoneValidator
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

public object CheckoutApiServiceFactory {

    public fun create(
        publicKey: String,
        environment: Environment,
        context: Context
    ): CheckoutApiService {
        val logger = EventLoggerProvider.provide()

        logger.setup(context, environment)

        return CheckoutApiClient(
            provideTokenRepository(publicKey, environment),
            provideThreeDSExecutor(logger)
        )
    }

    private fun provideTokenRepository(
        publicKey: String,
        environment: Environment
    ): TokenRepository = TokenRepositoryImpl(
        provideNetworkApiClient(publicKey, environment.url),
        CardToTokenRequestMapper(),
        CardTokenizationNetworkDataMapper(),
        ValidateTokenizationDataUseCase(
            CardValidatorFactory.createInternal(),
            AddressValidator(),
            PhoneValidator(),
            AddressToAddressValidationRequestDataMapper()
        )
    )

    private fun provideNetworkApiClient(
        publicKey: String,
        url: String
    ) = TokenNetworkApiClient(
        url,
        OkHttpProvider.createOkHttpClient(publicKey),
        Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    )

    private fun provideThreeDSExecutor(logger: Logger<LoggingEvent>): Executor<ThreeDSRequest> = ThreeDSExecutor(
        ProcessThreeDSUseCase(),
        ThreeDSEventLogger(logger)
    )
}
