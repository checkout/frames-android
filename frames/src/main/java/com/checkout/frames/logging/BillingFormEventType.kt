package com.checkout.frames.logging

import com.checkout.logging.LoggingEventType

internal enum class BillingFormEventType(override val eventId: String) : LoggingEventType {
    PRESENTED("billing_form_presented"),
    SUBMIT("billing_form_submit"),
    CANCELED("billing_form_cancelled"),
}
