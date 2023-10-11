package com.checkout.frames.screen.billingaddress.billingaddressform

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.checkout.frames.di.base.Injector
import com.checkout.frames.screen.billingaddress.billingaddressdetails.BillingAddressDetailsScreen
import com.checkout.frames.screen.countrypicker.CountryPickerScreen
import com.checkout.frames.screen.navigation.Screen
import com.checkout.frames.style.screen.BillingFormStyle

@SuppressWarnings("MagicNumber")
@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun BillingAddressFormScreen(
    style: BillingFormStyle,
    injector: Injector,
    onClose: () -> Unit,
) {
    val animationDuration = 250
    val childNavController = rememberNavController()

    NavHost(childNavController, startDestination = Screen.BillingFormDetails.route) {
        composable(
            Screen.BillingFormDetails.route,
        ) {
            BillingAddressDetailsScreen(
                style.billingAddressDetailsStyle,
                injector,
                childNavController,
            ) { onClose() }
        }
        composable(
            Screen.CountryPicker.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, tween(animationDuration))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, tween(animationDuration))
            },
        ) {
            CountryPickerScreen(
                style.countryPickerStyle,
                injector,
            ) {
                childNavController.navigateUp()
            }
        }
    }
}
