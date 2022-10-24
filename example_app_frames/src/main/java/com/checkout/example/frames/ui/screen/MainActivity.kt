package com.checkout.example.frames.ui.screen

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.checkout.example.frames.navigation.Screen
import com.checkout.example.frames.ui.utils.ENVIRONMENT
import com.checkout.example.frames.ui.utils.PUBLIC_KEY
import com.checkout.frames.screen.paymentform.PaymentFormConfig
import com.checkout.frames.screen.paymentform.PaymentFormScreen
import com.checkout.frames.style.screen.PaymentFormStyle
import com.checkout.frames.tokenization.TokenizationResultHandler
import com.checkout.tokenization.model.TokenDetails

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Navigator(this.applicationContext) }
    }
}

@Composable
fun Navigator(context: Context) {
    val navController = rememberNavController()
    val defaultPaymentFormConfig = PaymentFormConfig(
        publicKey = PUBLIC_KEY,
        context = context,
        environment = ENVIRONMENT,
        tokenizationResultHandler = object : TokenizationResultHandler {
            override fun onSuccess(tokenDetails: TokenDetails) {
                // TODO: Not yet implemented
            }

            override fun onFailure(errorMessage: String) {
                // TODO: Not yet implemented
            }
        },
        style = PaymentFormStyle()
    )

    NavHost(navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) { HomeScreen(navController) }
        composable(route = Screen.DefaultUI.route) { PaymentFormScreen(defaultPaymentFormConfig) }
    }
}
