package com.checkout.android_sdk.logging

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.checkout.android_sdk.Utils.CheckoutTheme
import com.checkout.android_sdk.Utils.Environment
import com.checkout.eventlogger.domain.model.MonitoringLevel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.*

@RunWith(RobolectricTestRunner::class)
class FramesLoggingEventsTest {
    private lateinit var mockContext: Context
    private lateinit var ckoTheme: CheckoutTheme


    @Before
    internal fun setUp() {
        mockContext = ApplicationProvider.getApplicationContext()
        ckoTheme = CheckoutTheme(mockContext)
    }

    @Test
    fun `test PaymentFormPresentedEvent map to correct values`() {

        assertEquals(getExpectedPaymentFormPresentedLoggingEvent().typeIdentifier,
            FramesLoggingEventDataProvider.logPaymentFormPresentedEvent(ckoTheme).typeIdentifier)

        assertEquals(getExpectedPaymentFormPresentedLoggingEvent().monitoringLevel,
            FramesLoggingEventDataProvider.logPaymentFormPresentedEvent(ckoTheme).monitoringLevel)

        assertEquals(getExpectedPaymentFormPresentedLoggingEvent().properties,
            FramesLoggingEventDataProvider.logPaymentFormPresentedEvent(ckoTheme).properties)

    }


    private fun getExpectedPaymentFormPresentedLoggingEvent(): FramesLoggingEvent {
        val checkoutTheme = CheckoutTheme(mockContext)
        val eventData = mapOf(
            PaymentFormLanguageEventAttribute.locale to Locale.getDefault().toString(),
            PaymentFormLanguageEventAttribute.colorPrimary to checkoutTheme.getColorPrimaryProperty(),
            PaymentFormLanguageEventAttribute.colorAccent to checkoutTheme.getColorAccentProperty(),
            PaymentFormLanguageEventAttribute.colorButtonNormal to checkoutTheme.getColorButtonNormalProperty(),
            PaymentFormLanguageEventAttribute.colorControlNormal to checkoutTheme.getColorButtonNormalProperty(),
            PaymentFormLanguageEventAttribute.textColorPrimary to checkoutTheme.getTextColorPrimaryProperty(),
            PaymentFormLanguageEventAttribute.colorControlActivated to checkoutTheme.getColorControlActivatedProperty(),
        )
        return FramesLoggingEvent(
            MonitoringLevel.INFO,
            FramesLoggingEventType.PAYMENT_FORM_PRESENTED,
            properties = eventData
        )
    }

    @Test
    fun `test PaymentFormPresentedEvent property initialization`() {
        val checkoutTheme = CheckoutTheme(mockContext)

        val eventData = mapOf(
            PaymentFormLanguageEventAttribute.locale to null,
            PaymentFormLanguageEventAttribute.colorPrimary to null,
            PaymentFormLanguageEventAttribute.colorAccent to checkoutTheme.getColorAccentProperty(),
            PaymentFormLanguageEventAttribute.colorButtonNormal to checkoutTheme.getColorButtonNormalProperty(),
            PaymentFormLanguageEventAttribute.colorControlNormal to checkoutTheme.getColorControlNormalProperty(),
            PaymentFormLanguageEventAttribute.textColorPrimary to checkoutTheme.getTextColorPrimaryProperty(),
            PaymentFormLanguageEventAttribute.colorControlActivated to checkoutTheme.getColorControlActivatedProperty(),
        )
        assertNull(eventData[PaymentFormLanguageEventAttribute.locale])

        assertNull(eventData[PaymentFormLanguageEventAttribute.colorPrimary])

        assertNotEquals(eventData[PaymentFormLanguageEventAttribute.locale].toString(),
            FramesLoggingEventDataProvider.logPaymentFormPresentedEvent(ckoTheme).properties[PaymentFormLanguageEventAttribute.locale])

        assertNotEquals(eventData[PaymentFormLanguageEventAttribute.colorPrimary].toString(),
            FramesLoggingEventDataProvider.logPaymentFormPresentedEvent(ckoTheme).properties[PaymentFormLanguageEventAttribute.colorPrimary])
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