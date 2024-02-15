package com.checkout.logging

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.checkout.base.model.Environment
import com.checkout.eventlogger.CheckoutEventLogger
import com.checkout.eventlogger.domain.model.MetadataKey
import com.checkout.eventlogger.domain.model.RemoteProcessorMetadata
import com.checkout.logging.model.LoggingEvent
import com.checkout.logging.utils.toLoggingEnvironment
import com.checkout.logging.utils.toLoggingName
import java.util.UUID

internal class EventLogger(private val logger: CheckoutEventLogger) : Logger<LoggingEvent> {

    @VisibleForTesting
    val sentLogs = hashSetOf<String>()

    @VisibleForTesting
    var needToSetup = true

    override fun setup(
        context: Context,
        environment: Environment,
        identifier: String,
        version: String,
    ) {
        if (needToSetup) {
            logger.enableRemoteProcessor(
                environment.toLoggingEnvironment(),
                provideProcessorMetadata(context, environment, identifier, version),
            )
            resetSession()
            needToSetup = false
        }
    }

    override fun resetSession() {
        val correlationId = UUID.randomUUID().toString()
        logger.addMetadata(MetadataKey.correlationId, correlationId)
        sentLogs.clear()
    }

    override fun log(event: LoggingEvent) = logger.logEvent(event)

    override fun logOnce(event: LoggingEvent) {
        if (sentLogs.add(event.eventType.eventId)) log(event)
    }

    private fun provideProcessorMetadata(
        context: Context,
        environment: Environment,
        identifier: String,
        version: String,
    ) = RemoteProcessorMetadata.from(
        context,
        environment.toLoggingName(),
        identifier,
        version,
    )
}
