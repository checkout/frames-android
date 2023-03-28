package com.checkout.frames.screen.billingaddress.billingaddressdetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.checkout.frames.component.billingaddressfields.BillingAddressDynamicInputComponent
import com.checkout.frames.component.country.CountryComponent
import com.checkout.frames.di.base.Injector
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingFormFields
import com.checkout.frames.screen.navigation.Screen
import com.checkout.frames.style.screen.BillingAddressDetailsStyle
import com.checkout.frames.style.view.InternalButtonViewStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import com.checkout.frames.utils.extensions.ResetFocus
import com.checkout.frames.view.InternalButton
import com.checkout.frames.view.InternalButtonState
import com.checkout.frames.view.TextLabel
import com.checkout.frames.view.TextLabelState

@Composable
internal fun BillingAddressDetailsScreen(
    style: BillingAddressDetailsStyle,
    injector: Injector,
    navController: NavHostController,
    onClose: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val resetFocus = remember { mutableStateOf(false) }

    val viewModel: BillingAddressDetailsViewModel = viewModel(
        factory = BillingAddressDetailsViewModel.Factory(injector, style)
    )

    if (viewModel.goBack.value) onClose()

    Column(
        modifier = viewModel.screenModifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clickable(
                interactionSource = interactionSource, indication = null
            ) { resetFocus.value = true }
    ) {
        HeaderComponent(
            screenTitleStyle = viewModel.screenTitleStyle,
            screenTitleState = viewModel.screenTitleState,
            screenButtonStyle = viewModel.screenButtonStyle,
            screenButtonState = viewModel.screenButtonState,
            onTapDoneButton = viewModel::onTapDoneButton
        )

        LazyColumn(
            modifier = viewModel.inputComponentsContainerModifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            itemsIndexed(items = viewModel.inputComponentsStateList) { index, state ->
                if (state.addressFieldName == BillingFormFields.Country.name) {
                    CountryComponent(
                        style = style.countryComponentStyle,
                        injector = injector,
                        onCountryUpdated = { country -> viewModel.updateCountryComponentState(state, country) },
                        goToCountryPicker = { navController.navigate(Screen.CountryPicker.route) },
                    )
                } else {
                    BillingAddressDynamicInputComponent(
                        position = index,
                        inputComponentViewStyle = viewModel.inputComponentViewStyleList[index],
                        inputComponentState = state,
                        onFocusChanged = viewModel::onFocusChanged,
                        onValueChange = viewModel::onAddressFieldTextChange
                    )
                }
            }
        }
    }

    if (resetFocus.value) {
        ResetFocus()
        resetFocus.value = false
    }
}

@Composable
private fun HeaderComponent(
    screenTitleStyle: TextLabelViewStyle,
    screenTitleState: TextLabelState,
    screenButtonStyle: InternalButtonViewStyle,
    screenButtonState: InternalButtonState,
    onTapDoneButton: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        TextLabel(style = screenTitleStyle, state = screenTitleState)
        InternalButton(style = screenButtonStyle, state = screenButtonState) {
            onTapDoneButton()
        }
    }
}
