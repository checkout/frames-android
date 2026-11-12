package com.checkout

import android.content.Context
import com.checkout.api.CheckoutApiClient
import com.checkout.api.CheckoutApiService
import com.checkout.base.model.Environment
import com.checkout.logging.EventLoggerProvider
import com.checkout.logging.Logger
import com.checkout.logging.model.LoggingEvent
import com.checkout.logging.utils.toBaseUrl
import com.checkout.network.OkHttpProvider
import com.checkout.risk.FramesOptions
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
import com.checkout.tokenization.usecase.RiskInstanceProvider
import com.checkout.tokenization.usecase.RiskSdkUseCase
import com.checkout.tokenization.usecase.ValidateCVVTokenizationDataUseCase
import com.checkout.tokenization.usecase.ValidateTokenizationDataUseCase
import com.checkout.validation.validator.AddressValidator
import com.checkout.validation.validator.PhoneValidator
import com.squareup.moshi.Moshi

public object CheckoutApiServiceFactory {
    private lateinit var correlationId: String
    private lateinit var riskSDKFramesOptions: FramesOptions

    /**
     * Creates [CheckoutApiService].
     *
     * @param publicKey the public API key used for client-side authentication.
     * @param environment the environment for tokenization.
     * @param context the Android context.
     * @param baseUrlPrefix an optional merchant-specific subdomain label (e.g., "mssd"), not a URL or hostname.
     * Must contain 1 to 63 ASCII letters, digits, or hyphens, starting and ending with a letter or digit.
     * Applied to both tokenization and Risk requests. Null, empty, or invalid values use the default hosts.
     */
    @JvmStatic
    public fun create(
        publicKey: String,
        environment: Environment,
        context: Context,
        baseUrlPrefix: String? = null,
    ): CheckoutApiService {
        val validatedBaseUrlPrefix = BaseUrlPrefixValidator.validPrefixOrNull(baseUrlPrefix)
        val logger = EventLoggerProvider.provide()
        logger.setup(context, environment)
        correlationId = logger.correlationId
        riskSDKFramesOptions = FramesOptions(
            version = BuildConfig.PRODUCT_VERSION,
            productIdentifier = BuildConfig.PRODUCT_IDENTIFIER,
            correlationId,
        )

        return CheckoutApiClient(
            provideTokenRepository(context, publicKey, environment, validatedBaseUrlPrefix),
            provideThreeDSExecutor(logger),
        )
    }

    private fun provideTokenRepository(
        context: Context,
        publicKey: String,
        environment: Environment,
        baseUrlPrefix: String?,
    ): TokenRepository = TokenRepositoryImpl(
        networkApiClient = provideNetworkApiClient(publicKey, environment.toBaseUrl(baseUrlPrefix)),
        cardToTokenRequestMapper = CardToTokenRequestMapper(),
        cvvToTokenNetworkRequestMapper = CVVToTokenNetworkRequestMapper(),
        cardTokenizationNetworkDataMapper = CardTokenizationNetworkDataMapper(),
        validateTokenizationDataUseCase = ValidateTokenizationDataUseCase(
            CardValidatorFactory.createInternal(),
            AddressValidator(),
            PhoneValidator(),
            AddressToAddressValidationRequestDataMapper(),
        ),
        validateCVVTokenizationDataUseCase = ValidateCVVTokenizationDataUseCase(
            CVVComponentValidatorFactory.create(),
        ),
        logger = TokenizationEventLogger(EventLoggerProvider.provide()),
        publicKey = publicKey,
        cvvTokenizationNetworkDataMapper = CVVTokenizationNetworkDataMapper(),
        riskSdkUseCase = RiskSdkUseCase(
            environment,
            context,
            publicKey,
            riskSDKFramesOptions,
            RiskInstanceProvider,
            baseUrlPrefix,
        ),
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
