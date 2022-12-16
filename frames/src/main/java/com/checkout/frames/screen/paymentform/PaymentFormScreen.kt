package com.checkout.frames.screen.paymentform

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.checkout.frames.screen.billingaddress.billingaddressform.BillingAddressFormScreen
import com.checkout.frames.screen.navigation.Screen
import com.checkout.frames.screen.paymentdetails.PaymentDetailsScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@SuppressWarnings("MagicNumber")
@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun PaymentFormScreen(config: PaymentFormConfig) {
    val animationDuration = 350
    val navController = rememberAnimatedNavController()
    val viewModel: PaymentFormViewModel = viewModel(
        factory = PaymentFormViewModel.Factory(
            config.publicKey,
            config.context,
            config.environment,
            config.paymentFlowHandler,
            config.supportedCardSchemeList
        )
    )

    AnimatedNavHost(navController, startDestination = Screen.PaymentDetails.route) {
        composable(Screen.PaymentDetails.route) {
            PaymentDetailsScreen(
                config.style.paymentDetailsStyle,
                viewModel.injector,
                navController
            )
        }

        composable(
            Screen.BillingFormScreen.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, tween(animationDuration))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, tween(animationDuration))
            }
        ) {
            BillingAddressFormScreen(
                config.style.billingFormStyle,
                viewModel.injector
            ) {
                navController.navigateUp()
            }
        }
    }
}
