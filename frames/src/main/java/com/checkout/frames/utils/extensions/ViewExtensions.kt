package com.checkout.frames.utils.extensions

import android.graphics.Rect
import android.view.View

@SuppressWarnings("MagicNumber")
internal fun View.isKeyboardOpen(): Boolean {
    val screenPercentage = 15
    val rect = Rect()
    getWindowVisibleDisplayFrame(rect)

    val screenHeight = rootView.height
    val keypadHeight = screenHeight - rect.bottom

    return keypadHeight > screenHeight * screenPercentage / 100
}
