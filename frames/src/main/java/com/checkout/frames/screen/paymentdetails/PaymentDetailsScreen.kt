package com.checkout.frames.screen.paymentdetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.checkout.frames.component.country.CountryComponent
import com.checkout.frames.di.base.Injector
import com.checkout.frames.screen.navigation.Screen
import com.checkout.frames.style.screen.PaymentDetailsStyle
import com.checkout.frames.utils.constants.PaymentDetailsScreenConstants
import com.checkout.frames.view.TextLabel

@SuppressWarnings("MagicNumber")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun PaymentDetailsScreen(
    style: PaymentDetailsStyle,
    injector: Injector,
    navController: NavController
) {
    val interactionSource = remember { MutableInteractionSource() }
    val resetFocus = remember { mutableStateOf(false) }
    val viewModel: PaymentDetailsViewModel = viewModel(
        factory = PaymentDetailsViewModel.Factory(injector, style)
    )

    Column(
        modifier = Modifier.clickable(
            interactionSource = interactionSource, indication = null
        ) { resetFocus.value = true }
    ) {
        TextLabel(style = viewModel.headerStyle, state = viewModel.headerState)

        Column(
            modifier = Modifier
                .padding(
                    start = PaymentDetailsScreenConstants.padding.dp,
                    end = PaymentDetailsScreenConstants.padding.dp
                )
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            viewModel.componentProvider.CardScheme(style = style.cardSchemeStyle)

            viewModel.componentProvider.CardNumber(style = style.cardNumberStyle)

            Spacer(modifier = Modifier.padding(top = 24.dp))

            viewModel.componentProvider.ExpiryDate(style = style.expiryDateComponentStyle)

            Spacer(modifier = Modifier.padding(top = 24.dp))

            viewModel.componentProvider.Cvv(style = style.cvvComponentStyle)

            Spacer(modifier = Modifier.padding(top = 24.dp))

            CountryComponent(style.countryComponentStyle, injector) {
                navController.navigate(Screen.CountryPicker.route)
            }

            Spacer(modifier = Modifier.padding(top = 24.dp))

            viewModel.componentProvider.AddressSummary(style.addressSummaryComponentStyle) {
                navController.navigate(Screen.BillingFormDetails.route)
            }

            Spacer(modifier = Modifier.padding(top = 32.dp))

            viewModel.componentProvider.PayButton(style = style.payButtonComponentStyle)
        }
    }

    if (resetFocus.value) {
        LocalSoftwareKeyboardController.current?.hide()
        LocalFocusManager.current.clearFocus(true)
        resetFocus.value = false
    }
}
