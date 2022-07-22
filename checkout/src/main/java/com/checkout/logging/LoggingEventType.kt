package com.checkout.logging

/**
 * Interface that should be implemented by all logging event types.
 */
internal interface LoggingEventType {
    /**
     * Event identifier.
     */
    val eventId: String
}
