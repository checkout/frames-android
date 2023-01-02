package com.checkout.frames.component.expirydate

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.autofill.AutofillType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.checkout.frames.component.base.InputComponent
import com.checkout.frames.di.base.Injector
import com.checkout.frames.style.component.ExpiryDateComponentStyle

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun ExpiryDateComponent(
    style: ExpiryDateComponentStyle,
    injector: Injector,
) {
    val viewModel: ExpiryDateViewModel = viewModel(
        factory = ExpiryDateViewModel.Factory(injector, style)
    )

    InputComponent(
        style = viewModel.componentStyle,
        state = viewModel.componentState.inputState,
        autofillType = AutofillType.CreditCardExpirationDate,
        onFocusChanged = viewModel::onFocusChanged,
        onValueChange = viewModel::onExpiryDateInputChange
    )
}
