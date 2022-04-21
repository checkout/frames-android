package com.checkout.android_sdk.logging

import com.checkout.eventlogger.CheckoutEventLogger
import com.checkout.eventlogger.domain.model.MonitoringLevel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

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
    fun `test PaymentFormPresentedEvent map to correct values`() {

        assertEquals(getExpectedPaymentFormPresentedLoggingEvent().typeIdentifier,
            FramesLoggingEventDataProvider.logPaymentFormPresentedEvent().typeIdentifier)

        assertEquals(getExpectedPaymentFormPresentedLoggingEvent().monitoringLevel,
            FramesLoggingEventDataProvider.logPaymentFormPresentedEvent().monitoringLevel)

        assertEquals(getExpectedPaymentFormPresentedLoggingEvent().properties[PaymentFormLanguageEventAttribute.locale],
            FramesLoggingEventDataProvider.logPaymentFormPresentedEvent().properties[PaymentFormLanguageEventAttribute.locale])

        sendSdkLoggerMockEvents(FramesLoggingEventDataProvider.logPaymentFormPresentedEvent())
    }

    @Test
    fun `test PaymentFormPresentedEvent successfully logged`() {
        val expectedEventCount = 1
        eventCount = 0
        sendSdkLoggerMockEvents(FramesLoggingEventDataProvider.logPaymentFormPresentedEvent())
        assertEquals(expectedEventCount, eventCount)
    }


    private fun getExpectedPaymentFormPresentedLoggingEvent(): FramesLoggingEvent {
        val eventData = mapOf(
            PaymentFormLanguageEventAttribute.locale to Locale.getDefault().toString()
        )
        return FramesLoggingEvent(
            MonitoringLevel.INFO,
            FramesLoggingEventType.PAYMENT_FORM_PRESENTED,
            properties = eventData
        )
    }

    private fun sendSdkLoggerMockEvents(event: FramesLoggingEvent) {
        sdkLoggerMock.logEvent(event)
        eventCount++
    }

}