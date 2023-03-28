package com.checkout.frames.component.country

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.checkout.base.model.Country
import com.checkout.frames.component.base.InputComponent
import com.checkout.frames.di.base.Injector
import com.checkout.frames.style.component.CountryComponentStyle

@Composable
internal fun CountryComponent(
    style: CountryComponentStyle,
    injector: Injector,
    onCountryUpdated: (country: Country) -> Unit,
    goToCountryPicker: () -> Unit,
) {
    val viewModel: CountryViewModel = viewModel(
        factory = CountryViewModel.Factory(injector, style)
    )

    viewModel.prepare(onCountryUpdated)

    with(viewModel.componentStyle.inputFieldStyle) {
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) { goToCountryPicker() }
    }

    InputComponent(
        style = viewModel.componentStyle,
        state = viewModel.componentState,
        onValueChange = {}
    )
}
