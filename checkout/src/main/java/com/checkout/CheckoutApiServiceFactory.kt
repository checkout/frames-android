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
import com.checkout.tokenization.logging.TokenizationEventLogger
import com.checkout.tokenization.mapper.request.AddressToAddressValidationRequestDataMapper
import com.checkout.tokenization.mapper.request.CVVToTokenNetworkRequestMapper
import com.checkout.tokenization.mapper.request.CardToTokenRequestMapper
import com.checkout.tokenization.mapper.response.CVVTokenizationNetworkDataMapper
import com.checkout.tokenization.mapper.response.CardTokenizationNetworkDataMapper
import com.checkout.tokenization.repository.TokenRepository
import com.checkout.tokenization.repository.TokenRepositoryImpl
import com.checkout.tokenization.usecase.RiskSdkUseCase
import com.checkout.tokenization.usecase.ValidateCVVTokenizationDataUseCase
import com.checkout.tokenization.usecase.ValidateTokenizationDataUseCase
import com.checkout.validation.validator.AddressValidator
import com.checkout.validation.validator.PhoneValidator
import com.squareup.moshi.Moshi

public object CheckoutApiServiceFactory {

    @JvmStatic
    public fun create(
        publicKey: String,
        environment: Environment,
        context: Context,
    ): CheckoutApiService {
        val logger = EventLoggerProvider.provide()

        logger.setup(context, environment)

        return CheckoutApiClient(
            provideTokenRepository(context, publicKey, environment),
            provideThreeDSExecutor(logger),
        )
    }

    private fun provideTokenRepository(
        context: Context,
        publicKey: String,
        environment: Environment,
    ): TokenRepository = TokenRepositoryImpl(
//        context = context,
//        environment = environment,
        networkApiClient = provideNetworkApiClient(publicKey, environment.url),
        cardToTokenRequestMapper = CardToTokenRequestMapper(),
        cvvToTokenNetworkRequestMapper = CVVToTokenNetworkRequestMapper(),
        cardTokenizationNetworkDataMapper = CardTokenizationNetworkDataMapper(),
        validateTokenizationDataUseCase = ValidateTokenizationDataUseCase(
            CardValidatorFactory.createInternal(),
            AddressValidator(),
            PhoneValidator(),
            AddressToAddressValidationRequestDataMapper(),
        ),
        validateCVVTokenizationDataUseCase = ValidateCVVTokenizationDataUseCase(CVVComponentValidatorFactory.create()),
        logger = TokenizationEventLogger(EventLoggerProvider.provide()),
        publicKey = publicKey,
        cvvTokenizationNetworkDataMapper = CVVTokenizationNetworkDataMapper(),
        riskSdkUseCase = RiskSdkUseCase(environment, context, publicKey),
    )

    private fun provideNetworkApiClient(
        publicKey: String,
        url: String,
    ) = TokenNetworkApiClient(
        url,
        OkHttpProvider.createOkHttpClient(publicKey),
        Moshi.Builder().build(),
    )

    private fun provideThreeDSExecutor(logger: Logger<LoggingEvent>): Executor<ThreeDSRequest> = ThreeDSExecutor(
        ProcessThreeDSUseCase(),
        ThreeDSEventLogger(logger),
    )
}
