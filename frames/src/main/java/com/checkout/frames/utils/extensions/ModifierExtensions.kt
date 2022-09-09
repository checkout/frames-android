package com.checkout.frames.utils.extensions

import android.view.ViewTreeObserver
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView

internal fun Modifier.clearFocusOnKeyboardDismiss(): Modifier = composed {
    var isFocused by remember { mutableStateOf(false) }
    var keyboardAppearedSinceLastFocused by remember { mutableStateOf(false) }

    if (isFocused) {
        val isKeyboardOpen by rememberKeyboardOpenState()
        val focusManager = LocalFocusManager.current

        LaunchedEffect(isKeyboardOpen) {
            if (isKeyboardOpen) keyboardAppearedSinceLastFocused = true
            else if (keyboardAppearedSinceLastFocused) focusManager.clearFocus()
        }
    }

    onFocusEvent {
        if (isFocused != it.isFocused) {
            isFocused = it.isFocused
            if (isFocused) keyboardAppearedSinceLastFocused = false
        }
    }
}

@Composable
private fun rememberKeyboardOpenState(): State<Boolean> = with(LocalView.current) {
    produceState(isKeyboardOpen()) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener { value = isKeyboardOpen() }

        viewTreeObserver.addOnGlobalLayoutListener(listener)
        awaitDispose { viewTreeObserver.removeOnGlobalLayoutListener(listener) }
    }
}
