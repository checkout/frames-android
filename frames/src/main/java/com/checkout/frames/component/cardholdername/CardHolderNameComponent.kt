package com.checkout.frames.component.cardholdername

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.checkout.frames.component.base.InputComponent
import com.checkout.frames.di.base.Injector
import com.checkout.frames.style.component.CardHolderNameComponentStyle

@Composable
internal fun CardHolderNameComponent(
    style: CardHolderNameComponentStyle,
    injector: Injector,
) {
    val viewModel: CardHolderNameViewModel = viewModel(
        factory = CardHolderNameViewModel.Factory(injector, style),
    )

    InputComponent(
        style = viewModel.componentStyle,
        state = viewModel.componentState.inputState,
        onFocusChanged = viewModel::onFocusChanged,
        onValueChange = viewModel::onCardHolderNameChange,
    )
}
