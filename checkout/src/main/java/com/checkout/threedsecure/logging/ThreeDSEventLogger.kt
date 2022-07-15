package com.checkout.threedsecure.logging

import com.checkout.eventlogger.domain.model.MonitoringLevel
import com.checkout.logging.Logger
import com.checkout.logging.model.LoggingEvent
import com.checkout.logging.utils.putErrorAttributes
import com.checkout.logging.utils.SUCCESS
import com.checkout.logging.utils.TOKEN_ID

internal class ThreeDSEventLogger(
    private val logger: Logger<LoggingEvent>
) : ThreeDSLogger {

    override fun logPresentedEvent() = logEvent(ThreeDSEventType.PRESENTED)

    override fun logLoadedEvent(success: Boolean, error: Throwable?) =
        logEvent(ThreeDSEventType.LOADED, success, error)

    override fun logCompletedEvent(success: Boolean, token: String?, error: Throwable?) =
        logEvent(ThreeDSEventType.COMPLETED, success, error, token)

    private fun logEvent(
        eventType: ThreeDSEventType,
        success: Boolean? = null,
        error: Throwable? = null,
        token: String? = null
    ) = logger.log(provideLoggingEvent(eventType, success, token, error))

    private fun provideLoggingEvent(
        eventType: ThreeDSEventType,
        success: Boolean?,
        token: String?,
        error: Throwable?
    ): LoggingEvent {
        val properties = hashMapOf<String, Any>()

        success?.let { properties[SUCCESS] = it }
        token?.let { properties[TOKEN_ID] = it }
        error?.let { properties.putErrorAttributes(it) }

        return LoggingEvent(
            eventType,
            if (error == null) MonitoringLevel.INFO else MonitoringLevel.ERROR,
            properties
        )
    }
}
