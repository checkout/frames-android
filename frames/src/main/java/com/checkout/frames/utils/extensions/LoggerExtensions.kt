package com.checkout.frames.utils.extensions

import androidx.compose.ui.text.intl.Locale
import com.checkout.logging.Logger
import com.checkout.logging.LoggingEventType
import com.checkout.logging.model.LoggingEvent

internal const val LOGGING_LOCALE_KEY = "locale"

internal fun Logger<LoggingEvent>.logEvent(event: LoggingEventType) {
    log(LoggingEvent(event))
}

internal fun Logger<LoggingEvent>.logEventWithLocale(event: LoggingEventType) {
    val deviceLanguage = Locale.current.language
    log(LoggingEvent(event, properties = mapOf(LOGGING_LOCALE_KEY to deviceLanguage)))
}
