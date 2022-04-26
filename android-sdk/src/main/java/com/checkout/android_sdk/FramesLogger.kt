package com.checkout.android_sdk

import android.content.Context
import com.checkout.android_sdk.Request.TokenType
import com.checkout.android_sdk.Response.TokenisationFail
import com.checkout.android_sdk.Response.TokenisationResponse
import com.checkout.android_sdk.Utils.Environment
import com.checkout.android_sdk.Utils.filterNotNullValues
import com.checkout.android_sdk.logging.FramesLoggingEvent
import com.checkout.android_sdk.logging.FramesLoggingEventDataProvider
import com.checkout.android_sdk.logging.FramesLoggingEventType
import com.checkout.android_sdk.logging.LoggingEventAttribute
import com.checkout.eventlogger.CheckoutEventLogger
import com.checkout.eventlogger.METADATA_CORRELATION_ID
import com.checkout.eventlogger.domain.model.Event
import com.checkout.eventlogger.domain.model.MessageEvent
import com.checkout.eventlogger.domain.model.MonitoringLevel.*
import com.checkout.eventlogger.domain.model.RemoteProcessorMetadata
import com.checkout.eventlogger.Environment as LoggerEnvironment

object CheckoutAPILogging {
    /**
     * Enable logging errors to logcat.
     *
     * Should be configured in [android.app.Application.onCreate] or before any
     * CheckoutApi or PaymentForm classes are used.
     */
    @JvmStatic
    var errorLoggingEnabled = false
}

internal class FramesLogger {

    private lateinit var sdkLogger: CheckoutEventLogger

    companion object {
        private const val PRODUCT_NAME = "frames-android-sdk"
        private const val PRODUCT_VERSION = BuildConfig.PRODUCT_VERSION
        private const val PRODUCT_IDENTIFIER = "com.checkout.frames-mobile-sdk"

        @JvmStatic
        fun log(logAction: Runnable) {
            try {
                logAction.run()
            } catch (e: Exception) {
                // Suppress error
            }
        }

        fun getProductName(): String {
            return PRODUCT_NAME
        }
    }

    fun initialise(
        context: Context,
        environment: Environment,
        checkoutSdkLogger: CheckoutEventLogger,
    ) {
        val loggingEnvironment = environment.toLoggingEnvironment()
        val remoteProcessorMetadata = RemoteProcessorMetadata.from(
            context,
            environment.toEnvironmentName(),
            PRODUCT_IDENTIFIER,
            PRODUCT_VERSION
        )
        sdkLogger = checkoutSdkLogger
        sdkLogger.enableRemoteProcessor(
            loggingEnvironment,
            remoteProcessorMetadata
        )
    }

    private fun internalAnalyticsEvent(event: Event) {
        sdkLogger.logEvent(event)
    }

    fun clear() {
        sdkLogger.clearMetadata()
    }

    private fun addMetadata(metadata: String, value: String) =
        sdkLogger.addMetadata(metadata, value)

    /**
     * Resets all metadata and assigns the new [correlationID] to identify this logging session.
     */
    fun initialiseLoggingSession(correlationID: String) {
        clear()
        addMetadata(METADATA_CORRELATION_ID, correlationID)
    }

    fun sendPaymentFormPresentedEvent() {
        internalAnalyticsEvent(
            FramesLoggingEventDataProvider.logPaymentFormPresentedEvent()
        )
    }

    fun sendBillingFormPresentedEvent() {
        internalAnalyticsEvent(
            FramesLoggingEvent(
                INFO,
                FramesLoggingEventType.BILLING_FORM_PRESENTED
            )
        )
    }

    fun sendTokenRequestedEvent(tokenType: TokenType, publicKey: String) {
        addMetadata(LoggingEventAttribute.publicKey, publicKey)

        val eventData = mapOf(
            LoggingEventAttribute.tokenType to tokenType.value
        )

        internalAnalyticsEvent(
            FramesLoggingEvent(
                INFO,
                FramesLoggingEventType.TOKEN_REQUESTED,
                eventData
            )
        )
    }

    fun sendTokenResponseEvent(
        responseCode: Int,
        successResponse: TokenisationResponse?,
        failedResponse: TokenisationFail?
    ) {
        val eventData = mutableMapOf<String, Any?>()
        successResponse?.let {
            eventData[LoggingEventAttribute.tokenType] = successResponse.type
            eventData[LoggingEventAttribute.scheme] = successResponse.scheme
            eventData[LoggingEventAttribute.tokenID] = successResponse.token
        }
        eventData[LoggingEventAttribute.httpStatusCode] = responseCode.toString()
        failedResponse?.let {
            eventData[LoggingEventAttribute.serverError] = mapOf(
                LoggingEventAttribute.requestID to failedResponse.requestId,
                LoggingEventAttribute.errorType to failedResponse.errorType,
                LoggingEventAttribute.errorCodes to failedResponse.errorCodes?.let {
                    if (it.isEmpty()) null else it
                },
            ).filterNotNullValues()
        }

        internalAnalyticsEvent(
            FramesLoggingEvent(
                INFO,
                FramesLoggingEventType.TOKEN_RESPONSE,
                eventData.filterNotNullValues()
            )
        )
    }

    @JvmOverloads
    fun debugEvent(
        message: String,
        throwable: Throwable? = null
    ) {
        val event = MessageEvent(
            monitoringLevel = DEBUG,
            typeIdentifier = "debug",
            message = message,
            cause = throwable
        )
        sdkLogger.logEvent(event)
    }

    @JvmOverloads
    fun errorEvent(
        message: String,
        throwable: Throwable? = null
    ) {
        val event = MessageEvent(
            monitoringLevel = ERROR,
            typeIdentifier = "exception",
            message = message,
            cause = throwable
        )
        sdkLogger.logEvent(event)
    }
}

private fun Environment.toLoggingEnvironment() = when (this) {
    Environment.SANDBOX -> LoggerEnvironment.SANDBOX
    Environment.LIVE -> LoggerEnvironment.PRODUCTION
}

private fun Environment.toEnvironmentName() = when (this) {
    Environment.SANDBOX -> "sandbox"
    Environment.LIVE -> "production"
}
