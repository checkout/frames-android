package com.checkout.threedsecure.logging

import com.checkout.logging.LoggingEventType

internal enum class ThreeDSEventType(override val eventId: String) : LoggingEventType {
    PRESENTED("3ds_webview_presented"),
    LOADED("3ds_challenge_loaded"),
    COMPLETED("3ds_challenge_complete")
}
