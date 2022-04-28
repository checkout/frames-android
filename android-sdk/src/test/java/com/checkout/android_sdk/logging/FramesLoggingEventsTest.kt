package com.checkout.android_sdk.logging

import com.checkout.android_sdk.Utils.Environment
import com.checkout.eventlogger.domain.model.MonitoringLevel
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Locale

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

    @Test
    fun `test threedsWebviewPresented event map to correct values`() {
        val expectedThreedsWebViewPresentedLoggingEvent = FramesLoggingEvent(
            MonitoringLevel.INFO,
            FramesLoggingEventType.THREEDS_WEBVIEW_PRESENTED)

        assertEquals(expectedThreedsWebViewPresentedLoggingEvent.typeIdentifier,
            FramesLoggingEventDataProvider.logThreedsWebviewPresentedEvent().typeIdentifier)

        assertEquals(expectedThreedsWebViewPresentedLoggingEvent.monitoringLevel,
            FramesLoggingEventDataProvider.logThreedsWebviewPresentedEvent().monitoringLevel)

        assertEquals(expectedThreedsWebViewPresentedLoggingEvent.properties,
            FramesLoggingEventDataProvider.logThreedsWebviewPresentedEvent().properties)
    }

    @Test
    fun `test threedsWebviewLoaded event map to correct values`() {
        val eventData = mapOf(
            WebviewEventAttribute.success to true,
        )
        val expectedThreedsWebViewLoadedLoggingEvent = FramesLoggingEvent(
            MonitoringLevel.INFO,
            FramesLoggingEventType.THREEDS_WEBVIEW_LOADED,
            eventData)

        assertEquals(expectedThreedsWebViewLoadedLoggingEvent.typeIdentifier,
            FramesLoggingEventDataProvider.logThreedsWebviewLoadedEvent(true).typeIdentifier)

        assertEquals(expectedThreedsWebViewLoadedLoggingEvent.monitoringLevel,
            FramesLoggingEventDataProvider.logThreedsWebviewLoadedEvent(true).monitoringLevel)

        assertEquals(expectedThreedsWebViewLoadedLoggingEvent.properties,
            FramesLoggingEventDataProvider.logThreedsWebviewLoadedEvent(true).properties)
    }

    @Test
    fun `test threedsWebviewComplete event map to correct values`() {
        val eventData = mapOf(
            WebviewEventAttribute.tokenID to "cko-session-ID",
            WebviewEventAttribute.success to true,
        )

        val threedsWebViewCompleteLoggingEvent = FramesLoggingEvent(
            MonitoringLevel.INFO,
            FramesLoggingEventType.THREEDS_WEBVIEW_COMPLETE,
            eventData)

        assertEquals(threedsWebViewCompleteLoggingEvent.typeIdentifier,
            FramesLoggingEventDataProvider.logThreedsWebviewCompleteEvent("cko-session-ID",
                true).typeIdentifier)

        assertEquals(threedsWebViewCompleteLoggingEvent.monitoringLevel,
            FramesLoggingEventDataProvider.logThreedsWebviewCompleteEvent("cko-session-ID",
                true).monitoringLevel)

        assertEquals(threedsWebViewCompleteLoggingEvent.properties,
            FramesLoggingEventDataProvider.logThreedsWebviewCompleteEvent("cko-session-ID",
                true).properties)
    }
}