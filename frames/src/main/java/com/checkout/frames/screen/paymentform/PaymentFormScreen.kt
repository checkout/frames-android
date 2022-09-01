package com.checkout.frames.screen.paymentform

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.checkout.frames.screen.navigation.Screen
import com.checkout.frames.screen.paymentdetails.PaymentDetailsScreen

@Composable
public fun PaymentFormScreen(config: PaymentFormConfig) {
    val navController = rememberNavController()
    val viewModel: PaymentFormViewModel = viewModel(
        factory = PaymentFormViewModel.Factory(
            config.publicKey,
            config.context,
            config.environment
        )
    )

    NavHost(navController, startDestination = Screen.PaymentDetails.route) {
        composable(Screen.PaymentDetails.route) {
            PaymentDetailsScreen(
                style = config.style.paymentDetailsStyle,
                viewModel.injector
            )
        }
    }
}
