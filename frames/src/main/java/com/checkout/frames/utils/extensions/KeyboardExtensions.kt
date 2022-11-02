package com.checkout.frames.utils.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun ResetFocus() {
    LocalSoftwareKeyboardController.current?.hide()
    LocalFocusManager.current.clearFocus(true)
}
