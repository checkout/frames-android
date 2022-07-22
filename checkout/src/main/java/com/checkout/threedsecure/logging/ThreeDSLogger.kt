package com.checkout.threedsecure.logging

/**
 * Used to log events related to 3DS process.
 */
internal interface ThreeDSLogger {

    /**
     * Logs event WebView presented for the first time.
     */
    fun logPresentedEvent()

    /**
     * Logs event when initial url is loaded for the first time.
     *
     * @param success - Indicates state of the loading process.
     * @param error - [Throwable] error with detailed cause of the issue.
     */
    fun logLoadedEvent(success: Boolean, error: Throwable? = null)

    /**
     * Logs event when 3DS flow is completed.
     *
     * @param success - Indicates state of the loading process.
     * @param token - [String] token data which can be obtained only after success 3DS flow.
     * @param error - [Throwable] error with detailed cause of the issue.
     */
    fun logCompletedEvent(success: Boolean, token: String? = null, error: Throwable? = null)
}
