package com.checkout.frames.screen.paymentform

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.checkout.frames.screen.billingaddress.billingaddressform.BillingAddressFormScreen
import com.checkout.frames.screen.navigation.Screen
import com.checkout.frames.screen.paymentdetails.PaymentDetailsScreen
import com.checkout.frames.screen.paymentform.model.PaymentFormConfig

@SuppressWarnings("MagicNumber")
@Composable
internal fun PaymentFormScreen(config: PaymentFormConfig) {
    val animationDuration = 350
    val navController = rememberNavController()
    val viewModel: PaymentFormViewModel = viewModel(
        factory = PaymentFormViewModel.Factory(
            publicKey = config.publicKey,
            context = config.context,
            environment = config.environment,
            paymentFlowHandler = config.paymentFlowHandler,
            supportedCardSchemes = config.supportedCardSchemeList,
            prefillData = config.prefillData,
        ),
    )

    NavHost(navController, startDestination = Screen.PaymentDetails.route) {
        composable(Screen.PaymentDetails.route) {
            PaymentDetailsScreen(
                config.style.paymentDetailsStyle,
                viewModel.injector,
                navController,
            )
        }

        composable(
            Screen.BillingFormScreen.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(animationDuration))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(animationDuration))
            },
        ) {
            BillingAddressFormScreen(
                config.style.billingFormStyle,
                viewModel.injector,
            ) {
                navController.navigateUp()
            }
        }
    }
}
