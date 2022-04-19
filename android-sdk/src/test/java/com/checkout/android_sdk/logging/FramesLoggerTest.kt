package com.checkout.android_sdk.logging

import com.checkout.eventlogger.domain.model.MonitoringLevel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class FramesLoggerTest {

    @Test
    fun `test PaymentFormPresentedEvent map to correct values`() {
        val eventData = mapOf(
            PaymentFormLanguageEventAttribute.locale to "de_DE"
        )

        val framesLoggingEvent = FramesLoggingEvent(
            MonitoringLevel.INFO,
            FramesLoggingEventType.PAYMENT_FORM_PRESENTED,
            properties = eventData
        )

        // Event should not be null
        assertNotNull(framesLoggingEvent)
        // Asserting properties of event
        assertEquals("de_DE", framesLoggingEvent.properties["locale"])
        // Asserting typeIdentifier for event
        assertEquals(FramesLoggingEventType.PAYMENT_FORM_PRESENTED.eventId,
            framesLoggingEvent.typeIdentifier)
    }


}