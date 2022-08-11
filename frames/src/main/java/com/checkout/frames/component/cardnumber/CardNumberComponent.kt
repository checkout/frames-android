package com.checkout.frames.component.cardnumber

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.checkout.frames.di.base.Injector
import com.checkout.frames.style.component.InputComponentStyle

@Composable
internal fun CardNumberComponent(
    style: InputComponentStyle,
    injector: Injector
) {
    val viewModel: CardNumberViewModel = viewModel(
        factory = CardNumberViewModel.Factory(injector, style)
    )

    // TODO: PIMOB-1349 - Payment form component: card number
    println(viewModel.javaClass.name)
}
