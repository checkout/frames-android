package com.checkout.android_sdk.logging
import com.checkout.android_sdk.Utils.Environment
import com.checkout.eventlogger.domain.model.MonitoringLevel
import java.util.Locale

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

    fun logThreedsWebviewLoadedEvent(
        isSuccessFullWebviewLoadingOrCompletionEvents: Boolean,
    ): FramesLoggingEvent {
        val eventData = mapOf(
            WebviewEventAttribute.success to isSuccessFullWebviewLoadingOrCompletionEvents,
        )
        return FramesLoggingEvent(
            if (isSuccessFullWebviewLoadingOrCompletionEvents) MonitoringLevel.INFO else MonitoringLevel.ERROR,
            FramesLoggingEventType.THREEDS_WEBVIEW_LOADED,
            eventData
        )
    }

    fun logThreedsWebviewCompleteEvent(
        tokenID: String?,
        isSuccessFullWebviewLoadingOrCompletionEvents: Boolean,
    ): FramesLoggingEvent {
        val tokenIDToPass = tokenID ?: ""
        val eventData = mapOf(
            WebviewEventAttribute.tokenID to tokenIDToPass,
            WebviewEventAttribute.success to isSuccessFullWebviewLoadingOrCompletionEvents,
        )
        return FramesLoggingEvent(
            if (isSuccessFullWebviewLoadingOrCompletionEvents) MonitoringLevel.INFO else MonitoringLevel.ERROR,
            FramesLoggingEventType.THREEDS_WEBVIEW_COMPLETE,
            eventData
        )
    }

    fun logThreedsWebviewPresentedEvent(
    ): FramesLoggingEvent {
        return FramesLoggingEvent(
            MonitoringLevel.INFO,
            FramesLoggingEventType.THREEDS_WEBVIEW_PRESENTED
        )
    }

}