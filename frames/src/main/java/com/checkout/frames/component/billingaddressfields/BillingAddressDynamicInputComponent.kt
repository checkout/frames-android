package com.checkout.frames.component.billingaddressfields

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.checkout.frames.component.base.InputComponent
import com.checkout.frames.style.view.BillingAddressInputComponentViewStyle

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun BillingAddressDynamicInputComponent(
    position: Int,
    inputComponentViewStyle: BillingAddressInputComponentViewStyle,
    inputComponentState: BillingAddressInputComponentState,
    onFocusChanged: (Int, Boolean) -> Unit,
    onValueChange: (Int, String) -> Unit
) {
    InputComponent(
        style = inputComponentViewStyle.inputComponentViewStyle,
        state = inputComponentState.inputComponentState,
        onFocusChanged = { onFocusChanged(position, it) },
        onValueChange = { onValueChange(position, it) }
    )
}
