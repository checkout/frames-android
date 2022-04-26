package com.checkout.android_sdk.logging
import com.checkout.eventlogger.domain.model.MonitoringLevel
import com.checkout.android_sdk.Utils.Environment
import java.util.*

object FramesLoggingEventDataProvider {

    fun logPaymentFormPresentedEvent(): FramesLoggingEvent {
        val eventData = mapOf(
            PaymentFormLanguageEventAttribute.locale to Locale.getDefault().toString(),
        )
        return FramesLoggingEvent(
            MonitoringLevel.INFO,
            FramesLoggingEventType.PAYMENT_FORM_PRESENTED,
            eventData
          )
    }

    fun logCheckoutApiClientInitialisedEvent(environment: Environment): FramesLoggingEvent {
        val eventData = mapOf(
            CheckoutApiClientInitEventAttribute.environment to environment
        )
        return FramesLoggingEvent(
            MonitoringLevel.INFO,
            FramesLoggingEventType.CHECKOUT_API_CLIENT_INITIALISED,
            eventData
        )
    }

}