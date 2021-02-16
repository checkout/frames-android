package com.checkout.android_sdk.logging

import com.checkout.eventlogger.domain.model.Event
import com.checkout.eventlogger.domain.model.MonitoringLevel
import java.util.*

internal class FramesLoggingEvent(
    override val monitoringLevel: MonitoringLevel,
    eventType: FramesLoggingEventType,
    override val properties: Map<String, Any> = emptyMap(),
    override val time: Date = Date()
) : Event {

    override val typeIdentifier: String = eventType.eventId

}