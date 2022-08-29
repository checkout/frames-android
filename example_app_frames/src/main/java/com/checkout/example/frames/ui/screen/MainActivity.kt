package com.checkout.example.frames.ui.screen

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.checkout.base.model.Environment
import com.checkout.example.frames.navigation.Screen
import com.checkout.frames.screen.paymentform.PaymentFormConfig
import com.checkout.frames.screen.paymentform.PaymentFormScreen
import com.checkout.frames.style.screen.PaymentFormStyle

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
        publicKey = "",
        context = context,
        environment = Environment.SANDBOX,
        style = PaymentFormStyle()
    )

    NavHost(navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) { HomeScreen(navController) }
        composable(route = Screen.DefaultUI.route) { PaymentFormScreen(defaultPaymentFormConfig) }
    }
}
