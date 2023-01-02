package com.checkout.frames.component.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import com.checkout.frames.style.view.InputComponentViewStyle
import com.checkout.frames.view.InputField
import com.checkout.frames.view.TextLabel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun InputComponent(
    style: InputComponentViewStyle,
    state: InputComponentState,
    autofillType: AutofillType? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
    onValueChange: (String) -> Unit
) = with(state) {
    Column(modifier = style.containerModifier.wrapContentHeight()) {
        Row {
            Column(
                modifier = Modifier
                    .weight(1F)
                    .wrapContentHeight()
            ) {
                // Title label
                if (titleState.isVisible.value) TextLabel(style.titleStyle, titleState)
                // Subtitle label
                if (subtitleState.isVisible.value) TextLabel(style.subtitleStyle, subtitleState)
            }
            Column(
                Modifier
                    .align(Alignment.Bottom)
                    .wrapContentHeight()
            ) {
                // Info label
                if (infoState.isVisible.value) TextLabel(style.infoStyle, infoState)
            }
        }
        // Input field
        InputField(style.inputFieldStyle, inputFieldState, autofillType, onFocusChanged, onValueChange)
        // Error message
        if (errorState.isVisible.value) TextLabel(style.errorMessageStyle, errorState)
    }
}
