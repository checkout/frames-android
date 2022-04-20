package com.checkout.android_sdk

import com.checkout.android_sdk.Utils.Environment
import com.checkout.android_sdk.logging.CheckoutApiClientInitEventAttribute
import com.checkout.android_sdk.logging.FramesLoggingEventType
import com.checkout.eventlogger.domain.model.MonitoringLevel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class FramesLoggerTest {

    @Test
    fun `test CheckoutApiClientInitialised event map to correct values`() {
        val eventData = mapOf(
            CheckoutApiClientInitEventAttribute.environment to Environment.SANDBOX
        )

        val checkoutApiClientInitialisedLoggingEvent = TestFramesLoggingEvent(
            MonitoringLevel.INFO,
            FramesLoggingEventType.CHECKOUT_API_CLIENT_INITIALISED,
            properties = eventData)

        // Event should not be null
        assertNotNull(checkoutApiClientInitialisedLoggingEvent)
        // Asserting properties of event
        assertEquals(Environment.SANDBOX,
            checkoutApiClientInitialisedLoggingEvent.properties["environment"])
        // Asserting monitoring level of event
        assertEquals(MonitoringLevel.INFO, checkoutApiClientInitialisedLoggingEvent.monitoringLevel)
        // Asserting typeIdentifier for event
        assertEquals(FramesLoggingEventType.CHECKOUT_API_CLIENT_INITIALISED.eventId,
            checkoutApiClientInitialisedLoggingEvent.typeIdentifier)

    }

}