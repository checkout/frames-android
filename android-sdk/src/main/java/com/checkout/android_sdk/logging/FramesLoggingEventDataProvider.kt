package com.checkout.android_sdk.logging

import com.checkout.android_sdk.Utils.Environment
import com.checkout.eventlogger.domain.model.MonitoringLevel

object FramesLoggingEventDataProvider {

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