package com.checkout.android_sdk.Utils

import android.view.KeyEvent
import android.view.View

internal class BackNavigationHandler(
    private val backNavigationListener: BackNavigationListener
) {

    fun processKeyEvent(event: KeyEvent, view: View): Boolean {
        // Ignore all but BACK key events.
        if (event.keyCode != KeyEvent.KEYCODE_BACK)  return false

        if (event.action == KeyEvent.ACTION_DOWN && event.repeatCount == 0) {
            view.keyDispatcherState?.startTracking(event, view)
        } else if (event.action == KeyEvent.ACTION_UP) {
            view.keyDispatcherState?.handleUpEvent(event)
            if (event.isTracking && !event.isCanceled) {
                backNavigationListener.onBackEventDetected(event, view)
                return true
            }
        }
        return false
    }

    interface BackNavigationListener {
        /**
         * Invoked when a back navigation is detected.
         */
        fun onBackEventDetected(event: KeyEvent, view: View)
    }
}