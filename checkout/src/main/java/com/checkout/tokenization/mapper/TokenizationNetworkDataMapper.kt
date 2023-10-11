package com.checkout.tokenization.mapper

import com.checkout.base.error.CheckoutError
import com.checkout.network.error.NetworkError
import com.checkout.network.response.NetworkApiResponse
import com.checkout.tokenization.error.TokenizationError
import com.checkout.tokenization.error.TokenizationError.Companion.INVALID_KEY
import com.checkout.tokenization.error.TokenizationError.Companion.INVALID_TOKEN_REQUEST
import com.checkout.tokenization.error.TokenizationError.Companion.TOKENIZATION_API_RESPONSE_PROCESSING_ERROR
import com.checkout.tokenization.model.TokenResult

/**
 * Base implementation of mapping from data object to domain classes.
 */
internal abstract class TokenizationNetworkDataMapper<S : Any> {

    @Suppress("TooGenericExceptionCaught")
    fun <T : Any> toTokenResult(result: NetworkApiResponse<T>): TokenResult<S> =
        try {
            when (result) {
                is NetworkApiResponse.Success -> createSuccessResult(result.body)
                is NetworkApiResponse.ServerError -> createServerErrorResult(result)
                is NetworkApiResponse.NetworkError -> createNetworkErrorResult(result)
                is NetworkApiResponse.InternalError -> createInternalErrorResult(result)
            }
        } catch (exception: Exception) {
            createExceptionErrorResult(exception)
        }

    protected abstract fun <T : Any> createMappedResult(resultBody: T): S

    private fun createExceptionErrorResult(exception: Exception): TokenResult<S> {
        val tokenError = if (exception is CheckoutError) {
            exception
        } else {
            CheckoutError(
                errorCode = TOKENIZATION_API_RESPONSE_PROCESSING_ERROR,
                message = exception.message,
                throwable = exception,
            )
        }
        return TokenResult.Failure(tokenError)
    }

    private fun <T : Any> createSuccessResult(resultBody: T): TokenResult<S> =
        TokenResult.Success(createMappedResult(resultBody))

    @Suppress("MagicNumber")
    private fun createServerErrorResult(serverError: NetworkApiResponse.ServerError): TokenResult<S> {
        val error = when (serverError.code) {
            401 -> TokenizationError(
                INVALID_KEY,
                serverError.toErrorMessage("Invalid key"),
            )

            else -> TokenizationError(
                INVALID_TOKEN_REQUEST,
                serverError.toErrorMessage("Token request failed"),
            )
        }
        return TokenResult.Failure(error)
    }

    private fun createNetworkErrorResult(networkError: NetworkApiResponse.NetworkError): TokenResult.Failure {
        val tokenError = when (networkError.throwable) {
            is CheckoutError -> networkError.throwable
            else -> NetworkError(
                errorCode = NetworkError.CONNECTION_FAILED_ERROR,
                message = networkError.throwable.message,
                cause = networkError.throwable,
            )
        }

        return TokenResult.Failure(tokenError)
    }

    private fun createInternalErrorResult(internalError: NetworkApiResponse.InternalError): TokenResult.Failure {
        val internalTokenError = when (internalError.throwable) {
            is CheckoutError -> internalError.throwable
            else -> TokenizationError(
                errorCode = TokenizationError.GOOGLE_PAY_REQUEST_PARSING_ERROR,
                message = internalError.throwable.message,
                cause = internalError.throwable,
            )
        }

        return TokenResult.Failure(internalTokenError)
    }

    private fun NetworkApiResponse.ServerError.toErrorMessage(errorMessage: String): String {
        return body?.errorType?.let {
            "$errorMessage - $it (HttpStatus: $code)"
        } ?: "$errorMessage - (HttpStatus: $code)"
    }
}
