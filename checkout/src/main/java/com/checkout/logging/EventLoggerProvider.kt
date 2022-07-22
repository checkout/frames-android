package com.checkout.logging

import com.checkout.BuildConfig
import com.checkout.eventlogger.CheckoutEventLogger
import com.checkout.eventlogger.domain.model.MonitoringLevel
import com.checkout.logging.model.LoggingEvent

/**
 * Provides the same event logger's state for the whole library.
 */
internal object EventLoggerProvider {

    private val eventLogger: Logger<LoggingEvent> by lazy {
        val logger = CheckoutEventLogger(BuildConfig.PRODUCT_NAME).apply {
            if (BuildConfig.DEFAULT_LOGCAT_MONITORING_ENABLED) enableLocalProcessor(MonitoringLevel.DEBUG)
        }

        EventLogger(logger)
    }

    internal fun provide(): Logger<LoggingEvent> = eventLogger
}
