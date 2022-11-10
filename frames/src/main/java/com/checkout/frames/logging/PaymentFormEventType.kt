package com.checkout.frames.logging

import com.checkout.logging.LoggingEventType

internal enum class PaymentFormEventType(override val eventId: String) : LoggingEventType {
    INITIALISED("payment_form_initialised"),
    PRESENTED("payment_form_presented"),
    SUBMITTED("payment_form_submitted"),
    CANCELED("payment_form_cancelled")
}
