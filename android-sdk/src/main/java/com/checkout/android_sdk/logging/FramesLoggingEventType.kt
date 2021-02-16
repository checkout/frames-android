package com.checkout.android_sdk.logging

internal enum class FramesLoggingEventType(val eventId: String) {
    PAYMENT_FORM_PRESENTED("payment_form_presented"),
    TOKEN_REQUESTED("token_requested"),
    TOKEN_RESPONSE("token_response"),
}