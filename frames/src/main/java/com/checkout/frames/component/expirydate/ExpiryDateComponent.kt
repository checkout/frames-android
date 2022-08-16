package com.checkout.frames.component.expirydate

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.checkout.frames.component.base.InputComponent
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.injector.FramesInjector
import com.checkout.frames.style.component.ExpiryDateComponentStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.base.InputFieldStyle

@Composable
internal fun ExpiryDateComponent(
    style: ExpiryDateComponentStyle,
    injector: Injector,
) {
    val viewModel: ExpiryDateViewModel = viewModel(
        factory = ExpiryDateViewModel.Factory(injector, style)
    )

    println(viewModel.javaClass.name)

    InputComponent(
        style = viewModel.componentStyle,
        state = viewModel.componentState.inputState,
        onValueChange = viewModel::onExpiryDateInputChange
    )
}

@SuppressWarnings("LongMethod", "MagicNumber")
@Preview(showBackground = true)
@Composable
private fun ExpiryDateComponentPreview() {
    ExpiryDateComponent(
        ExpiryDateComponentStyle(
            InputComponentStyle(inputFieldStyle = InputFieldStyle(placeholderText = "Test placeholder"))
        ),
        FramesInjector.create()
    )
}
