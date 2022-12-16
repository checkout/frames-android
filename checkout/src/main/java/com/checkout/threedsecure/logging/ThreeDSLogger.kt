package com.checkout.threedsecure.logging

import androidx.annotation.RestrictTo

/**
 * Used to log events related to 3DS process.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public interface ThreeDSLogger {

    /**
     * Logs event WebView presented for the first time.
     */
    public fun logPresentedEvent()

    /**
     * Logs event when initial url is loaded for the first time.
     *
     * @param success - Indicates state of the loading process.
     * @param error - [Throwable] error with detailed cause of the issue.
     */
    public fun logLoadedEvent(success: Boolean, error: Throwable? = null)

    /**
     * Logs event when 3DS flow is completed.
     *
     * @param success - Indicates state of the loading process.
     * @param token - [String] token data which can be obtained only after success 3DS flow.
     * @param error - [Throwable] error with detailed cause of the issue.
     */
    public fun logCompletedEvent(success: Boolean, token: String? = null, error: Throwable? = null)
}
