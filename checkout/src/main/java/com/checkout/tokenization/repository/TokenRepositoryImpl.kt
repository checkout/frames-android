package com.checkout.tokenization.repository

import androidx.annotation.VisibleForTesting
import com.checkout.BuildConfig
import com.checkout.base.mapper.Mapper
import com.checkout.base.usecase.UseCase
import com.checkout.network.response.NetworkApiResponse
import com.checkout.tokenization.NetworkApiClient
import com.checkout.tokenization.entity.GooglePayEntity
import com.checkout.tokenization.error.TokenizationError
import com.checkout.tokenization.logging.TokenizationLogger
import com.checkout.tokenization.mapper.TokenizationNetworkDataMapper
import com.checkout.tokenization.model.CVVTokenDetails
import com.checkout.tokenization.model.CVVTokenizationRequest
import com.checkout.tokenization.model.CVVTokenizationResultHandler
import com.checkout.tokenization.model.Card
import com.checkout.tokenization.model.CardTokenRequest
import com.checkout.tokenization.model.GooglePayTokenRequest
import com.checkout.tokenization.model.TokenDetails
import com.checkout.tokenization.model.TokenResult
import com.checkout.tokenization.model.ValidateCVVTokenizationRequest
import com.checkout.tokenization.request.CVVTokenNetworkRequest
import com.checkout.tokenization.request.GooglePayTokenNetworkRequest
import com.checkout.tokenization.request.TokenRequest
import com.checkout.tokenization.response.CVVTokenDetailsResponse
import com.checkout.tokenization.response.TokenDetailsResponse
import com.checkout.tokenization.usecase.RiskSdkUseCase
import com.checkout.tokenization.utils.TokenizationConstants
import com.checkout.validation.model.ValidationResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject

@Suppress("LongParameterList")
internal class TokenRepositoryImpl(
    private val networkApiClient: NetworkApiClient,
    private val cardToTokenRequestMapper: Mapper<Card, TokenRequest>,
    private val cvvToTokenNetworkRequestMapper: Mapper<CVVTokenizationRequest, CVVTokenNetworkRequest>,
    private val cardTokenizationNetworkDataMapper: TokenizationNetworkDataMapper<TokenDetails>,
    private val validateTokenizationDataUseCase: UseCase<Card, ValidationResult<Unit>>,
    private val validateCVVTokenizationDataUseCase: UseCase<ValidateCVVTokenizationRequest, ValidationResult<Unit>>,
    private val logger: TokenizationLogger,
    private val publicKey: String,
    private val cvvTokenizationNetworkDataMapper: TokenizationNetworkDataMapper<CVVTokenDetails>,
    private val riskSdkUseCase: RiskSdkUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : TokenRepository {
    @VisibleForTesting
    var networkCoroutineScope =
        CoroutineScope(
            CoroutineName(BuildConfig.PRODUCT_IDENTIFIER) +
                Dispatchers.IO +
                NonCancellable,
        )

    @Suppress("TooGenericExceptionCaught")
    override fun sendCardTokenRequest(cardTokenRequest: CardTokenRequest) {
        var response: NetworkApiResponse<TokenDetailsResponse>
        val tokenType = TokenizationConstants.CARD

        networkCoroutineScope.launch {
            val validationTokenizationDataResult = validateTokenizationDataUseCase.execute(cardTokenRequest.card)

            when (validationTokenizationDataResult) {
                is ValidationResult.Failure -> {
                    response = NetworkApiResponse.InternalError(validationTokenizationDataResult.error)
                }

                is ValidationResult.Success -> {
                    logger.logTokenRequestEvent(TokenizationConstants.CARD, publicKey)

                    response =
                        networkApiClient.sendCardTokenRequest(
                            cardToTokenRequestMapper.map(cardTokenRequest.card),
                        )

                    logResponse(response, TokenizationConstants.CARD)
                    logger.resetSession()
                }
            }

            val tokenResult = cardTokenizationNetworkDataMapper.toTokenResult(response)

            launch(Dispatchers.Main) {
                handleResponse(tokenType, tokenResult, cardTokenRequest.onSuccess, cardTokenRequest.onFailure)
            }
        }
    }

    @Suppress("TooGenericExceptionCaught")
    override fun sendCVVTokenizationRequest(cvvTokenizationRequest: CVVTokenizationRequest) {
        var response: NetworkApiResponse<CVVTokenDetailsResponse>

        with(cvvTokenizationRequest) {
            networkCoroutineScope.launch {
                val tokenType = TokenizationConstants.CVV
                val validateCVVRequest =
                    ValidateCVVTokenizationRequest(
                        cvv = cvv,
                        cardScheme = cardScheme,
                    )

                val validationTokenizationDataResult = validateCVVTokenizationDataUseCase.execute(validateCVVRequest)

                when (validationTokenizationDataResult) {
                    is ValidationResult.Failure -> {
                        response = NetworkApiResponse.InternalError(validationTokenizationDataResult.error)
                    }

                    is ValidationResult.Success -> {
                        logger.logTokenRequestEvent(tokenType, publicKey)

                        response =
                            networkApiClient.sendCVVTokenRequest(
                                cvvToTokenNetworkRequestMapper.map(from = cvvTokenizationRequest),
                            )

                        logCVVTokenizationResponse(response)
                        logger.resetSession()
                    }
                }

                val tokenResult = cvvTokenizationNetworkDataMapper.toTokenResult(response)

                launch(Dispatchers.Main) {
                    when (tokenResult) {
                        is TokenResult.Success -> {
                            try {
                                withContext(dispatcher) {
                                    riskSdkUseCase.execute(TokenResult.Success(tokenResult.result.token))
                                }
                            } catch (exception: Exception) {
                                logger.logErrorOnTokenRequestedEvent(tokenType, publicKey, exception)
                            } finally {
                                resultHandler(CVVTokenizationResultHandler.Success(tokenResult.result))
                            }
                        }

                        is TokenResult.Failure -> {
                            tokenResult.error.message?.let {
                                resultHandler(CVVTokenizationResultHandler.Failure(it))
                            }
                        }
                    }
                }
            }
        }
    }

    @Suppress("TooGenericExceptionCaught")
    override fun sendGooglePayTokenRequest(googlePayTokenRequest: GooglePayTokenRequest) {
        var response: NetworkApiResponse<TokenDetailsResponse>
        val tokenType = TokenizationConstants.GOOGLE_PAY

        networkCoroutineScope.launch {
            try {
                val request =
                    GooglePayTokenNetworkRequest(
                        TokenizationConstants.GOOGLE_PAY,
                        creatingTokenData(googlePayTokenRequest.tokenJsonPayload),
                    )

                logger.logTokenRequestEvent(TokenizationConstants.GOOGLE_PAY, publicKey)

                response = networkApiClient.sendGooglePayTokenRequest(request)

                logResponse(response, TokenizationConstants.GOOGLE_PAY)
                logger.resetSession()
            } catch (exception: Exception) {
                val error =
                    TokenizationError(
                        TokenizationError.GOOGLE_PAY_REQUEST_PARSING_ERROR,
                        exception.message,
                        exception.cause,
                    )
                response = NetworkApiResponse.InternalError(error)
                logger.logErrorOnTokenRequestedEvent(TokenizationConstants.GOOGLE_PAY, publicKey, error)
            }

            val tokenResult =
                cardTokenizationNetworkDataMapper.toTokenResult(
                    response,
                )

            launch(Dispatchers.Main) {
                handleResponse(tokenType, tokenResult, googlePayTokenRequest.onSuccess, googlePayTokenRequest.onFailure)
            }
        }
    }

    @VisibleForTesting
    @Throws(JSONException::class)
    fun creatingTokenData(tokenJsonPayload: String): GooglePayEntity {
        val tokenDataJsonObject = JSONObject(tokenJsonPayload)
        return GooglePayEntity(
            tokenDataJsonObject.getString("signature"),
            tokenDataJsonObject.getString("protocolVersion"),
            tokenDataJsonObject.getString("signedMessage"),
        )
    }

    private suspend fun handleResponse(
        tokenType: String,
        tokenResult: TokenResult<TokenDetails>,
        success: (tokenDetails: TokenDetails) -> Unit,
        failure: (errorMessage: String) -> Unit,
    ) {
        when (tokenResult) {
            is TokenResult.Success -> try {
                withContext(dispatcher) {
                    riskSdkUseCase.execute(TokenResult.Success(tokenResult.result.token))
                }
            } catch (exception: Exception) {
                logger.logErrorOnTokenRequestedEvent(tokenType, publicKey, exception)
            } finally {
                success(tokenResult.result)
            }

            is TokenResult.Failure -> {
                tokenResult.error.message?.let { failure(it) }
            }
        }
    }

    private fun logResponse(
        response: NetworkApiResponse<TokenDetailsResponse>,
        tokenType: String,
    ) {
        when (response) {
            is NetworkApiResponse.ServerError ->
                logger.logTokenResponseEvent(
                    tokenType = tokenType,
                    publicKey = publicKey,
                    tokenDetails = null,
                    cvvTokenDetailsResponse = null,
                    code = response.code,
                    errorResponse = response.body,
                )

            is NetworkApiResponse.Success ->
                logger.logTokenResponseEvent(
                    tokenType = tokenType,
                    publicKey = publicKey,
                    tokenDetails = response.body,
                )

            else -> {}
        }
    }

    private fun logCVVTokenizationResponse(response: NetworkApiResponse<CVVTokenDetailsResponse>) {
        when (response) {
            is NetworkApiResponse.ServerError ->
                logger.logTokenResponseEvent(
                    tokenType = TokenizationConstants.CVV,
                    publicKey = publicKey,
                    tokenDetails = null,
                    cvvTokenDetailsResponse = null,
                    code = response.code,
                    errorResponse = response.body,
                )

            is NetworkApiResponse.Success ->
                logger.logTokenResponseEvent(
                    tokenType = TokenizationConstants.CVV,
                    publicKey = publicKey,
                    tokenDetails = null,
                    cvvTokenDetailsResponse = response.body,
                )

            else -> {}
        }
    }
}
