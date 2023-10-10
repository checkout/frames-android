package com.checkout.logging.model

import androidx.annotation.RestrictTo
import com.checkout.eventlogger.domain.model.Event
import com.checkout.eventlogger.domain.model.MonitoringLevel
import com.checkout.logging.LoggingEventType
import java.util.Date

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class LoggingEvent constructor(
    internal val eventType: LoggingEventType,
    override val monitoringLevel: MonitoringLevel = MonitoringLevel.INFO,
    override val properties: Map<String, Any> = emptyMap(),
) : Event {

    override val time: Date = Date()

    override val typeIdentifier: String = eventType.eventId
}
