package com.checkout.api.logging

import com.checkout.logging.LoggingEventType

internal enum class ApiClientEventType(override val eventId: String) : LoggingEventType {
    INITIALIZE("checkout_api_client_initialised"),
}
