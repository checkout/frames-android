package com.checkout.android_sdk.logging

import com.checkout.android_sdk.Utils.Environment
import com.checkout.eventlogger.CheckoutEventLogger
import com.checkout.eventlogger.domain.model.MonitoringLevel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FramesLoggingEventsTest {

    @Mock
    private lateinit var sdkLoggerMock: CheckoutEventLogger

    private var eventCount = 0

    @Before
    fun setUp() {
        sdkLoggerMock = CheckoutEventLogger("frames-android-sdk-unit-test")
    }

    @Test
    fun `test checkoutApiClientInitialisedEvent map to correct values`() {
        val environment = Environment.SANDBOX

        assertEquals(getExpectedCheckoutApiClientInitialisedEvent(environment).typeIdentifier,
            FramesLoggingEventDataProvider.logCheckoutApiClientInitialisedEvent(environment).typeIdentifier)

        assertEquals(getExpectedCheckoutApiClientInitialisedEvent(environment).monitoringLevel,
            FramesLoggingEventDataProvider.logCheckoutApiClientInitialisedEvent(environment).monitoringLevel)

        assertEquals(getExpectedCheckoutApiClientInitialisedEvent(environment).properties[CheckoutApiClientInitEventAttribute.environment],
            FramesLoggingEventDataProvider.logCheckoutApiClientInitialisedEvent(environment).properties[CheckoutApiClientInitEventAttribute.environment])
    }


    fun sendFakeCheckoutApiClientInitialisedEvent(): Int {
        eventCount = 0
        sendSdkLoggerMockEvents(FramesLoggingEventDataProvider.logCheckoutApiClientInitialisedEvent(
            Environment.SANDBOX))
        return eventCount
    }

    private fun sendSdkLoggerMockEvents(event: FramesLoggingEvent) {
        sdkLoggerMock.logEvent(event)
        eventCount++
    }


    private fun getExpectedCheckoutApiClientInitialisedEvent(mEnvironment: Environment): FramesLoggingEvent {
        val eventData = mapOf(
            CheckoutApiClientInitEventAttribute.environment to mEnvironment
        )
        return FramesLoggingEvent(
            MonitoringLevel.INFO,
            FramesLoggingEventType.CHECKOUT_API_CLIENT_INITIALISED,
            properties = eventData
        )
    }
}