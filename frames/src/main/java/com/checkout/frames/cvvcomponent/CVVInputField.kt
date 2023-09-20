package com.checkout.frames.cvvcomponent

import androidx.compose.runtime.Composable
import com.checkout.frames.style.view.InputFieldViewStyle
import com.checkout.frames.view.InputField
import com.checkout.frames.view.InputFieldState

@Composable
internal fun CVVInputField(
    cvvFieldStyle: InputFieldViewStyle,
    cvvFieldState: InputFieldState,
    onFocusChanged: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
) {
    InputField(cvvFieldStyle, cvvFieldState, onFocusChanged, onValueChange)
}
