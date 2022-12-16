package com.checkout.frames.screen.billingaddress.billingaddressform

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import com.checkout.frames.di.base.Injector
import com.checkout.frames.screen.billingaddress.billingaddressdetails.BillingAddressDetailsScreen
import com.checkout.frames.screen.countrypicker.CountryPickerScreen
import com.checkout.frames.screen.navigation.Screen
import com.checkout.frames.style.screen.BillingFormStyle
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@SuppressWarnings("MagicNumber")
@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun BillingAddressFormScreen(
    style: BillingFormStyle,
    injector: Injector,
    onClose: () -> Unit
) {
    val animationDuration = 250
    val childNavController = rememberAnimatedNavController()

    AnimatedNavHost(childNavController, startDestination = Screen.BillingFormDetails.route) {
        composable(
            Screen.BillingFormDetails.route
        ) {
            BillingAddressDetailsScreen(
                style.billingAddressDetailsStyle,
                injector,
                childNavController
            ) { onClose() }
        }
        composable(
            Screen.CountryPicker.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up, tween(animationDuration))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, tween(animationDuration))
            }
        ) {
            CountryPickerScreen(
                style.countryPickerStyle,
                injector
            ) {
                childNavController.navigateUp()
            }
        }
    }
}
