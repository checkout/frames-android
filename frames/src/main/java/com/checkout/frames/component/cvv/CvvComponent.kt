package com.checkout.frames.component.cvv

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.autofill.AutofillType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.checkout.frames.component.base.InputComponent
import com.checkout.frames.di.base.Injector
import com.checkout.frames.style.component.CvvComponentStyle

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun CvvComponent(
    style: CvvComponentStyle,
    injector: Injector
) {
    val viewModel: CvvViewModel = viewModel(
        factory = CvvViewModel.Factory(injector, style)
    )

    viewModel.prepare()

    InputComponent(
        style = viewModel.componentStyle,
        state = viewModel.componentState.inputState,
        autofillType = AutofillType.CreditCardSecurityCode,
        onFocusChanged = viewModel::onFocusChanged,
        onValueChange = viewModel::onCvvChange
    )
}
