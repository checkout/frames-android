package com.checkout.frames.component.cardnumber

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.checkout.frames.component.base.InputComponent
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.injector.FramesInjector
import com.checkout.frames.style.component.CardNumberComponentStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.base.InputFieldStyle

@Composable
internal fun CardNumberComponent(
    style: CardNumberComponentStyle,
    injector: Injector
) {
    val viewModel: CardNumberViewModel = viewModel(
        factory = CardNumberViewModel.Factory(injector, style)
    )

    InputComponent(
        style = viewModel.componentStyle,
        state = viewModel.componentState.inputState,
        onFocusChanged = viewModel::onFocusChanged,
        onValueChange = viewModel::onCardNumberChange
    )
}

@SuppressWarnings("LongMethod", "MagicNumber")
@Preview(showBackground = true)
@Composable
private fun CardNumberComponentPreview() {
    CardNumberComponent(
        CardNumberComponentStyle(
            InputComponentStyle(inputFieldStyle = InputFieldStyle(placeholderText = "Test placeholder"))
        ),
        FramesInjector.create()
    )
}
