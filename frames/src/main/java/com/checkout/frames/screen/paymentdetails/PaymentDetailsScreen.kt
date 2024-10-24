package com.checkout.frames.screen.paymentdetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.checkout.frames.di.base.Injector
import com.checkout.frames.screen.navigation.Screen
import com.checkout.frames.style.screen.PaymentDetailsStyle
import com.checkout.frames.view.TextLabel

@SuppressWarnings("MagicNumber")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun PaymentDetailsScreen(
    style: PaymentDetailsStyle,
    injector: Injector,
    navController: NavController,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val resetFocus = remember { mutableStateOf(false) }
    val viewModel: PaymentDetailsViewModel = viewModel(
        factory = PaymentDetailsViewModel.Factory(injector, style),
    )

    val lifecycleOwner = LocalLifecycleOwner.current

    // Observe the lifecycle of the Composable
    val lifecycle = rememberUpdatedState(lifecycleOwner.lifecycle)

    // Create a DefaultLifecycleObserver to handle ON_RESUME
    val observer = rememberUpdatedState(
        object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                viewModel.resetCountrySelection()
            }
        },
    )

    DisposableEffect(lifecycleOwner) {
        lifecycle.value.addObserver(observer.value)

        onDispose {
            lifecycle.value.removeObserver(observer.value)
        }
    }

    Column(
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
        ) { resetFocus.value = true },
    ) {
        TextLabel(style = viewModel.headerStyle, state = viewModel.headerState)

        Column(
            modifier = viewModel.fieldsContainerModifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
        ) {
            // Cards schemes
            viewModel.componentProvider.CardScheme(style = style.cardSchemeStyle)

            // CardHolderName
            style.cardHolderNameStyle?.let { viewModel.componentProvider.CardHolderName(style = it) }

            // Card Number
            viewModel.componentProvider.CardNumber(style = style.cardNumberStyle)

            // Expiry Date
            viewModel.componentProvider.ExpiryDate(style = style.expiryDateStyle)

            // CVV
            style.cvvStyle?.let { viewModel.componentProvider.Cvv(style = it) }

            // Billing Address Summary
            style.addressSummaryStyle?.let { addressSummaryStyle ->
                viewModel.componentProvider.AddressSummary(style = addressSummaryStyle) {
                    navController.navigate(Screen.BillingFormScreen.route)
                }
            }

            // Pay Button
            viewModel.componentProvider.PayButton(style = style.payButtonStyle)
        }
    }

    if (resetFocus.value) {
        LocalSoftwareKeyboardController.current?.hide()
        LocalFocusManager.current.clearFocus(true)
        resetFocus.value = false
    }
}
