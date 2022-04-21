package com.checkout.android_sdk.logging

import com.checkout.eventlogger.domain.model.MonitoringLevel
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

}