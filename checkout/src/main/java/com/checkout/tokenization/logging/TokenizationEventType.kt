package com.checkout.tokenization.logging

import com.checkout.logging.LoggingEventType

internal enum class TokenizationEventType(override val eventId: String) : LoggingEventType {
    TOKEN_REQUESTED("token_requested"),
    TOKEN_RESPONSE("token_response"),
}
