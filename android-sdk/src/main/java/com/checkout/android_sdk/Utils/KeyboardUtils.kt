package com.checkout.android_sdk.Utils

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager

internal object KeyboardUtils {

    /**
     * Hides the soft keyboard using [InputMethodManager.hideSoftInputFromWindow]
     */
    @JvmStatic
    fun hideSoftKeyboard(view: View) : Boolean {
        return try {
            val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0) ?: false
        } catch (e: Exception) {
            Log.d("KeyboardUtils", "Failed to hide soft keyboard", e)
            false
        }
    }

    /**
     * Shows the soft keyboard using [InputMethodManager.showSoftInput]
     */
    @JvmStatic
    fun showSoftKeyboard(view: View) : Boolean {
        return try {
            val inputMethodManager =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.showSoftInput(view, 0) ?: false
        } catch (e: Exception) {
            Log.d("KeyboardUtils", "Failed to show soft keyboard", e)
            false
        }
    }
}