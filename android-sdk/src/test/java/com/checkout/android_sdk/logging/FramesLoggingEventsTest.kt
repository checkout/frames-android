package com.checkout.android_sdk.logging

import com.checkout.android_sdk.Utils.Environment
import com.checkout.eventlogger.domain.model.MonitoringLevel
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class FramesLoggingEventsTest {

  @Test
    fun `test PaymentFormPresentedEvent map to correct values`() {

        assertEquals(getExpectedPaymentFormPresentedLoggingEvent().typeIdentifier,
            FramesLoggingEventDataProvider.logPaymentFormPresentedEvent().typeIdentifier)

        assertEquals(getExpectedPaymentFormPresentedLoggingEvent().monitoringLevel,
            FramesLoggingEventDataProvider.logPaymentFormPresentedEvent().monitoringLevel)

        assertEquals(getExpectedPaymentFormPresentedLoggingEvent().properties,
            FramesLoggingEventDataProvider.logPaymentFormPresentedEvent().properties)

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

    @Test
    fun `test checkoutApiClientInitialisedEvent map to correct values`() {
        val environment = Environment.SANDBOX

        assertEquals(getExpectedCheckoutApiClientInitialisedEvent(environment).typeIdentifier,
            FramesLoggingEventDataProvider.logCheckoutApiClientInitialisedEvent(environment).typeIdentifier)

        assertEquals(getExpectedCheckoutApiClientInitialisedEvent(environment).monitoringLevel,
            FramesLoggingEventDataProvider.logCheckoutApiClientInitialisedEvent(environment).monitoringLevel)

        assertEquals(getExpectedCheckoutApiClientInitialisedEvent(environment).properties,
            FramesLoggingEventDataProvider.logCheckoutApiClientInitialisedEvent(environment).properties)
    }

    private fun getExpectedCheckoutApiClientInitialisedEvent(environment: Environment): FramesLoggingEvent {
        val eventData = mapOf(
            CheckoutApiClientInitEventAttribute.environment to environment
        )

        return FramesLoggingEvent(
            MonitoringLevel.INFO,
            FramesLoggingEventType.CHECKOUT_API_CLIENT_INITIALISED,
            properties = eventData
        )
    }
}