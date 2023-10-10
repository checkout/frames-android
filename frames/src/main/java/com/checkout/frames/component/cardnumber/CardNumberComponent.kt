package com.checkout.frames.component.cardnumber

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.checkout.frames.component.base.InputComponent
import com.checkout.frames.di.base.Injector
import com.checkout.frames.style.component.CardNumberComponentStyle

@Composable
internal fun CardNumberComponent(
    style: CardNumberComponentStyle,
    injector: Injector,
) {
    val viewModel: CardNumberViewModel = viewModel(
        factory = CardNumberViewModel.Factory(injector, style),
    )

    InputComponent(
        style = viewModel.componentStyle,
        state = viewModel.componentState.inputState,
        onFocusChanged = viewModel::onFocusChanged,
        onValueChange = viewModel::onCardNumberChange,
    )
}
