package com.checkout.frames.component.addresssummary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.checkout.frames.di.base.Injector
import com.checkout.frames.style.component.addresssummary.AddressSummaryComponentStyle
import com.checkout.frames.style.view.addresssummary.AddressSummarySectionViewStyle
import com.checkout.frames.view.InternalButton
import com.checkout.frames.view.InternalButtonState
import com.checkout.frames.view.TextLabel
import com.checkout.frames.view.TextLabelState

@Composable
internal fun AddressSummaryComponent(
    style: AddressSummaryComponentStyle,
    injector: Injector,
    goToBillingAddressForm: () -> Unit
) {
    val viewModel: AddressSummaryViewModel = viewModel(
        factory = AddressSummaryViewModel.Factory(injector, style)
    )

    viewModel.prepare()

    Column(modifier = viewModel.componentStyle.modifier.fillMaxWidth()) {
        // Title
        viewModel.componentStyle.titleStyle?.let {
            TextLabel(style = it, state = viewModel.componentState.titleState)
        }
        // Subtitle
        viewModel.componentStyle.subTitleStyle?.let {
            TextLabel(style = it, state = viewModel.componentState.subTitleState)
        }
        // Add address button or address preview
        if (viewModel.componentState.addressPreviewState.text.value.isBlank()) {
            InternalButton(
                style = viewModel.componentStyle.addAddressButtonStyle,
                state = viewModel.componentState.addAddressButtonState,
                onClick = goToBillingAddressForm
            )
        } else {
            AddressSummarySection(
                style = viewModel.componentStyle.summarySectionStyle,
                addressPreviewState = viewModel.componentState.addressPreviewState,
                editAddressButtonState = viewModel.componentState.editAddressButtonState,
                onEditButtonPress = goToBillingAddressForm
            )
        }
    }
}

@Composable
private fun AddressSummarySection(
    style: AddressSummarySectionViewStyle,
    addressPreviewState: TextLabelState,
    editAddressButtonState: InternalButtonState,
    onEditButtonPress: () -> Unit
) {
    Column(modifier = style.modifier) {
        // Address Preview
        TextLabel(style = style.addressTextStyle, state = addressPreviewState)
        // Divider
        style.dividerStyle?.let { Divider(it.modifier, it.color, it.thickness) }
        // Edit button
        InternalButton(
            style = style.editAddressButtonStyle,
            state = editAddressButtonState,
            onClick = onEditButtonPress
        )
    }
}
