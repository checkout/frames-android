package com.checkout.frames.screen.billingaddress.billingaddressdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.checkout.frames.di.base.Injector
import com.checkout.frames.component.billingaddressfields.BillingAddressDynamicInputComponent
import com.checkout.frames.style.screen.BillingAddressDetailsStyle
import com.checkout.frames.style.view.InternalButtonViewStyle
import com.checkout.frames.style.view.TextLabelViewStyle
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
    val viewModel: BillingAddressDetailsViewModel = viewModel(
        factory = BillingAddressDetailsViewModel.Factory(injector, style)
    )

    if (viewModel.goBack.value) {
        onClose()
    }

    Column(
        modifier = viewModel.screenModifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        HeaderComponent(
            screenTitleStyle = viewModel.screenTitleStyle,
            screenTitleState = viewModel.screenTitleState,
            screenButtonStyle = viewModel.screenButtonStyle,
            screenButtonState = viewModel.screenButtonState,
            onTapDoneButton = viewModel::onTapDoneButton
        )

        BillingAddressDynamicInputComponent(
            inputComponentViewStyleList = viewModel.inputComponentsViewContainerStyle.inputComponentViewStyleList,
            inputComponentStateList = viewModel.inputComponentsState.inputComponentStateList,
            countryComponentStyle = style.countryComponentStyle,
            onFocusChange = viewModel::onFocusChanged,
            onValueChange = viewModel::onSearchChange,
            modifier = viewModel.inputComponentsContainerModifier,
            injector = injector,
            navController = navController
        )
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
