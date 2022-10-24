package com.checkout.logging

import android.content.Context
import androidx.annotation.RestrictTo
import com.checkout.base.model.Environment

/**
 * Covers all main logging functionality among the library.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public interface Logger<T> {

    /**
     * Prepares logger for the further usage.
     *
     * @param context - [Context] of the application.
     * @param environment - [Environment] type.
     */
    public fun setup(context: Context, environment: Environment)

    /**
     * Resets logger session.
     */
    public fun resetSession()

    /**
     * Logs event.
     */
    public fun log(event: T)

    /**
     * Log event only one time.
     */
    public fun logOnce(event: T)
}
