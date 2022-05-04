package com.checkout.android_sdk.logging
import android.content.Context
import com.checkout.android_sdk.Utils.CheckoutTheme
import com.checkout.android_sdk.Utils.Environment
import com.checkout.android_sdk.Utils.filterNotNullValues
import com.checkout.eventlogger.domain.model.MonitoringLevel
import java.util.Locale

object FramesLoggingEventDataProvider {

    fun logPaymentFormPresentedEvent(context: Context): FramesLoggingEvent {
        val checkoutTheme = CheckoutTheme(context)
        val eventData = mapOf(
            PaymentFormLanguageEventAttribute.locale to Locale.getDefault().toString(),
            PaymentFormLanguageEventAttribute.colorPrimary to checkoutTheme.getColorPrimaryProperty(),
            PaymentFormLanguageEventAttribute.colorAccent to checkoutTheme.getColorAccentProperty(),
            PaymentFormLanguageEventAttribute.colorButtonNormal to checkoutTheme.getColorButtonNormalProperty(),
            PaymentFormLanguageEventAttribute.colorControlNormal to checkoutTheme.getColorControlNormalProperty(),
            PaymentFormLanguageEventAttribute.textColorPrimary to checkoutTheme.getTextColorPrimaryProperty(),
            PaymentFormLanguageEventAttribute.colorControlActivated to checkoutTheme.getColorControlActivatedProperty(),
        )
        return FramesLoggingEvent(
            MonitoringLevel.INFO,
            FramesLoggingEventType.PAYMENT_FORM_PRESENTED,
            eventData.filterNotNullValues()
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
        success: Boolean,
    ): FramesLoggingEvent {
        val eventData = mapOf(
            WebviewEventAttribute.success to success,
        )
        return FramesLoggingEvent(
            if (success) MonitoringLevel.INFO else MonitoringLevel.ERROR,
            FramesLoggingEventType.THREEDS_WEBVIEW_LOADED,
            eventData
        )
    }

    fun logThreedsWebviewCompleteEvent(
        tokenID: String?,
        success: Boolean,
    ): FramesLoggingEvent {
        val tokenId = tokenID ?: ""
        val eventData = mapOf(
            WebviewEventAttribute.tokenID to tokenId,
            WebviewEventAttribute.success to success,
        )
        return FramesLoggingEvent(
            if (success) MonitoringLevel.INFO else MonitoringLevel.ERROR,
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