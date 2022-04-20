package com.checkout.android_sdk

import com.checkout.android_sdk.Utils.Environment
import com.checkout.android_sdk.logging.CheckoutApiClientInitEventAttribute
import com.checkout.android_sdk.logging.FramesLoggingEventType
import com.checkout.eventlogger.domain.model.MonitoringLevel
import junit.framework.TestCase.*
import org.junit.Before
import org.junit.Test


internal class CheckoutApiClientTest {

    private lateinit var mockFramesLoggingEvents: MockFramesLoggingEvents

    private lateinit var environment: Environment


    @Before
    internal fun setUp() {
        environment = Environment.SANDBOX
    }

    @Test
    fun `test CheckoutAPIClient initialization Success`() {
        mockFramesLoggingEvents =
            MockFramesLoggingEvents(0, getActualCheckoutAPIClientInitLoggingEvent())
        assertNotNull(mockFramesLoggingEvents)

        // Verify event when it is called by mockFramesLoggingEvents
        mockFramesLoggingEvents.callCheckoutApiClientInitialisedEventTest()
        assertEquals(1, mockFramesLoggingEvents.eventLoggedCount)

        // Verify the environment properties of event
        val expectedEnvironment = Environment.SANDBOX
        assertEquals(expectedEnvironment,
            mockFramesLoggingEvents.callCheckoutApiClientInitialisedEventTest().properties[CheckoutApiClientInitEventAttribute.environment])

        // Verify success case when sending true event identifier
        assertEquals(getExpectedCheckoutAPIClientInitLoggingEvent().typeIdentifier,
            mockFramesLoggingEvents.callCheckoutApiClientInitialisedEventTest().typeIdentifier)
    }

    @Test
    fun `test CheckoutAPIClient initialization Failed`() {
        mockFramesLoggingEvents =
            MockFramesLoggingEvents(0, getActualCheckoutAPIClientInitLoggingEvent())
        assertNotNull(mockFramesLoggingEvents)

        // Verify event when it is not called by mockFramesLoggingEvents
        mockFramesLoggingEvents.callCheckoutApiClientInitialisedEventTest()
        assertNotSame(0, mockFramesLoggingEvents.eventLoggedCount)

        // Verify the environment properties of event
        val expectedEnvironment = Environment.LIVE
        assertNotSame(expectedEnvironment,
            mockFramesLoggingEvents.callCheckoutApiClientInitialisedEventTest().properties[CheckoutApiClientInitEventAttribute.environment])

        // Verify failure case when sending wrong event identifier
        assertNotSame("payment_form_presented",
            mockFramesLoggingEvents.callCheckoutApiClientInitialisedEventTest().typeIdentifier)
    }


    private fun getExpectedCheckoutAPIClientInitLoggingEvent(): TestFramesLoggingEvent {
        val eventData = mapOf(
            CheckoutApiClientInitEventAttribute.environment to Environment.SANDBOX
        )

        return TestFramesLoggingEvent(
            MonitoringLevel.INFO,
            FramesLoggingEventType.CHECKOUT_API_CLIENT_INITIALISED,
            properties = eventData
        )
    }

    private fun getActualCheckoutAPIClientInitLoggingEvent(): TestFramesLoggingEvent {
        val eventData = mapOf(
            CheckoutApiClientInitEventAttribute.environment to Environment.SANDBOX
        )

        return TestFramesLoggingEvent(
            MonitoringLevel.INFO,
            FramesLoggingEventType.CHECKOUT_API_CLIENT_INITIALISED,
            properties = eventData
        )
    }
}