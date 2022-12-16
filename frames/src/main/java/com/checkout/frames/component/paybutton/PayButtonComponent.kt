package com.checkout.frames.component.paybutton

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.checkout.frames.di.base.Injector
import com.checkout.frames.style.component.PayButtonComponentStyle
import com.checkout.frames.view.InternalButton

@Composable
internal fun PayButtonComponent(
    style: PayButtonComponentStyle,
    injector: Injector
) {
    val viewModel: PayButtonViewModel = viewModel(
        factory = PayButtonViewModel.Factory(injector, style)
    )

    viewModel.prepare()

    InternalButton(style = viewModel.buttonStyle, state = viewModel.buttonState) {
        viewModel.pay()
    }
}
