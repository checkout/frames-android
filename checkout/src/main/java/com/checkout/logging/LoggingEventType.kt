package com.checkout.logging

import androidx.annotation.RestrictTo

/**
 * Interface that should be implemented by all logging event types.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public interface LoggingEventType {
    /**
     * Event identifier.
     */
    public val eventId: String
}
