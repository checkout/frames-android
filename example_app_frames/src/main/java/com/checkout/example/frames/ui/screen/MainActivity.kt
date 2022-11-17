package com.checkout.example.frames.ui.screen

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.checkout.base.model.CardScheme
import com.checkout.example.frames.navigation.Screen
import com.checkout.example.frames.ui.utils.ENVIRONMENT
import com.checkout.example.frames.ui.utils.PUBLIC_KEY
import com.checkout.frames.R
import com.checkout.frames.screen.paymentform.PaymentFormConfig
import com.checkout.frames.api.PaymentFormMediator
import com.checkout.frames.style.screen.PaymentFormStyle
import com.checkout.frames.api.PaymentFlowHandler
import com.checkout.tokenization.model.TokenDetails

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigator(
                this.applicationContext,
                { showAlertDialog(this, getString(R.string.token_generated), it.token) },
                { showAlertDialog(this, getString(R.string.token_generated_failed), it) }
            )
        }
    }
}

@Composable
fun Navigator(
    context: Context,
    onSuccess: (TokenDetails) -> Unit,
    onFailure: (String) -> Unit
) {
    val navController = rememberNavController()
    val defaultPaymentFormConfig = PaymentFormConfig(
        publicKey = PUBLIC_KEY,
        context = context,
        environment = ENVIRONMENT,
        paymentFlowHandler = object : PaymentFlowHandler {
            override fun onSubmit() {
                /*Intentionally left empty*/
            }

            override fun onSuccess(tokenDetails: TokenDetails) = onSuccess(tokenDetails)
            override fun onFailure(errorMessage: String) = onFailure(errorMessage)
            override fun onBackPressed() {
                navController.navigateUp()
            }
        },
        style = PaymentFormStyle(),
        supportedCardSchemeList = listOf(
            CardScheme.VISA,
            CardScheme.DISCOVER,
            CardScheme.MASTERCARD,
            CardScheme.MAESTRO,
            CardScheme.AMERICAN_EXPRESS
        )
    )
    val paymentFormMediator = PaymentFormMediator(defaultPaymentFormConfig)

    NavHost(navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) { HomeScreen(navController) }
        composable(route = Screen.DefaultUI.route) { paymentFormMediator.PaymentForm() }
    }
}
