package com.checkout.logging

import android.content.Context
import com.checkout.base.model.Environment

/**
 * Covers all main logging functionality among the library.
 */
internal interface Logger<T> {

    /**
     * Prepares logger for the further usage.
     *
     * @param context - [Context] of the application.
     * @param environment - [Environment] type.
     */
    fun setup(context: Context, environment: Environment)

    /**
     * Resets logger session.
     */
    fun resetSession()

    /**
     * Logs event.
     */
    fun log(event: T)

    /**
     * Log event only one time.
     */
    fun logOnce(event: T)
}
