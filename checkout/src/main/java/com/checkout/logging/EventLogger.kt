package com.checkout.logging

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.checkout.BuildConfig
import com.checkout.base.model.Environment
import com.checkout.eventlogger.CheckoutEventLogger
import com.checkout.eventlogger.METADATA_CORRELATION_ID
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

    override fun setup(context: Context, environment: Environment) {
        if (needToSetup) {
            logger.enableRemoteProcessor(
                environment.toLoggingEnvironment(),
                provideProcessorMetadata(context, environment)
            )
            resetSession()
            needToSetup = false
        }
    }

    override fun resetSession() {
        val correlationId = UUID.randomUUID().toString()
        logger.addMetadata(METADATA_CORRELATION_ID, correlationId)
        sentLogs.clear()
    }

    override fun log(event: LoggingEvent) = logger.logEvent(event)

    override fun logOnce(event: LoggingEvent) {
        if (sentLogs.add(event.eventType.eventId)) log(event)
    }

    private fun provideProcessorMetadata(context: Context, environment: Environment) = RemoteProcessorMetadata.from(
        context,
        environment.toLoggingName(),
        BuildConfig.PRODUCT_IDENTIFIER,
        BuildConfig.PRODUCT_VERSION
    )
}
