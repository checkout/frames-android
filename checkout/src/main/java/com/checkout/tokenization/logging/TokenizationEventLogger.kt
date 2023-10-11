package com.checkout.tokenization.logging

import com.checkout.eventlogger.domain.model.MonitoringLevel
import com.checkout.logging.Logger
import com.checkout.logging.model.LoggingEvent
import com.checkout.logging.utils.ERROR_CODES
import com.checkout.logging.utils.ERROR_TYPE
import com.checkout.logging.utils.HTTP_STATUS_CODE
import com.checkout.logging.utils.PUBLIC_KEY
import com.checkout.logging.utils.REQUEST_ID
import com.checkout.logging.utils.SCHEME
import com.checkout.logging.utils.SERVER_ERROR
import com.checkout.logging.utils.TOKEN_ID
import com.checkout.logging.utils.TOKEN_TYPE
import com.checkout.logging.utils.putErrorAttributes
import com.checkout.network.response.ErrorResponse
import com.checkout.tokenization.response.CVVTokenDetailsResponse
import com.checkout.tokenization.response.TokenDetailsResponse

internal class TokenizationEventLogger(private val logger: Logger<LoggingEvent>) : TokenizationLogger {

    override fun logErrorOnTokenRequestedEvent(tokenType: String, publicKey: String, error: Throwable?) =
        logEvent(TokenizationEventType.TOKEN_REQUESTED, tokenType, publicKey, error)

    override fun logTokenRequestEvent(tokenType: String, publicKey: String) {
        logEvent(TokenizationEventType.TOKEN_REQUESTED, tokenType, publicKey)
    }

    override fun logTokenResponseEvent(
        tokenType: String,
        publicKey: String,
        tokenDetails: TokenDetailsResponse?,
        cvvTokenDetailsResponse: CVVTokenDetailsResponse?,
        code: Int?,
        errorResponse: ErrorResponse?,
    ) = logEvent(
        tokenizationEventType = TokenizationEventType.TOKEN_RESPONSE,
        tokenType = tokenType,
        publicKey = publicKey,
        error = null,
        tokenDetails = tokenDetails,
        cvvTokenDetailsResponse = cvvTokenDetailsResponse,
        code = code,
        errorResponse = errorResponse,
    )

    override fun resetSession() = logger.resetSession()

    private fun logEvent(
        tokenizationEventType: TokenizationEventType,
        tokenType: String,
        publicKey: String,
        error: Throwable? = null,
        tokenDetails: TokenDetailsResponse? = null,
        cvvTokenDetailsResponse: CVVTokenDetailsResponse? = null,
        code: Int? = null,
        errorResponse: ErrorResponse? = null,
    ) = logger.log(
        provideLoggingEvent(
            tokenizationEventType = tokenizationEventType,
            tokenType = tokenType,
            publicKey = publicKey,
            error = error,
            tokenDetails = tokenDetails,
            cvvTokenDetails = cvvTokenDetailsResponse,
            code = code,
            errorResponse = errorResponse,
        ),
    )

    private fun provideLoggingEvent(
        tokenizationEventType: TokenizationEventType,
        tokenType: String,
        publicKey: String,
        error: Throwable?,
        tokenDetails: TokenDetailsResponse?,
        cvvTokenDetails: CVVTokenDetailsResponse?,
        code: Int?,
        errorResponse: ErrorResponse?,
    ): LoggingEvent {
        val properties = hashMapOf<String, Any>()
        properties[TOKEN_TYPE] = tokenType
        properties[PUBLIC_KEY] = publicKey

        error?.let { properties.putErrorAttributes(it) }

        tokenDetails?.let {
            properties[TOKEN_ID] = it.token
            properties[SCHEME] = it.scheme ?: ""
        }

        cvvTokenDetails?.let {
            properties[TOKEN_ID] = it.token
        }

        code?.let {
            properties[HTTP_STATUS_CODE] = it
        }

        errorResponse?.let {
            properties[SERVER_ERROR] = mapOf(
                REQUEST_ID to it.requestId,
                ERROR_TYPE to it.errorType,
                ERROR_CODES to it.errorCodes,
            )
        }

        return LoggingEvent(
            tokenizationEventType,
            if (error == null) MonitoringLevel.INFO else MonitoringLevel.ERROR,
            properties,
        )
    }
}
