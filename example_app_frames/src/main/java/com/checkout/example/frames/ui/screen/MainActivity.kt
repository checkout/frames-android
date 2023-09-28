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
import com.checkout.base.model.Environment
import com.checkout.example.frames.navigation.Screen
import com.checkout.example.frames.paymentformstyling.CustomBillingFormStyle
import com.checkout.example.frames.paymentformstyling.CustomPaymentDetailsStyle
import com.checkout.example.frames.paymentformstyling.CustomPaymentFormTheme
import com.checkout.example.frames.ui.utils.ENVIRONMENT
import com.checkout.example.frames.ui.utils.PUBLIC_KEY
import com.checkout.example.frames.ui.utils.PrefillDataHelper
import com.checkout.frames.R
import com.checkout.frames.screen.paymentform.model.PaymentFormConfig
import com.checkout.frames.api.PaymentFormMediator
import com.checkout.frames.style.screen.PaymentFormStyle
import com.checkout.frames.api.PaymentFlowHandler
import com.checkout.frames.cvvinputfield.CVVComponentApiFactory
import com.checkout.frames.cvvinputfield.api.CVVComponentMediator
import com.checkout.frames.cvvinputfield.models.CVVComponentConfig
import com.checkout.frames.screen.paymentform.model.PrefillData
import com.checkout.frames.style.theme.paymentform.PaymentFormStyleProvider
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

@Suppress("LongMethod")
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
        ),
        prefillData = PrefillDataHelper.prefillData
    )
    val defaultPaymentFormMediator = PaymentFormMediator(defaultPaymentFormConfig)

    val customThemingPaymentFormMediator = PaymentFormMediator(
        defaultPaymentFormConfig.copy(
            style = PaymentFormStyleProvider.provide(CustomPaymentFormTheme.providePaymentFormTheme()),
            prefillData = PrefillData(cardHolderName = "Test Name")
        )
    )

    val customPaymentFormMediator = PaymentFormMediator(
        defaultPaymentFormConfig.copy(
            style = PaymentFormStyle(
                CustomPaymentDetailsStyle.providePaymentDetailsStyle(),
                CustomBillingFormStyle.provideBillingFormStyle()
            ),
            supportedCardSchemeList = listOf(
                CardScheme.VISA,
                CardScheme.MASTERCARD,
                CardScheme.AMERICAN_EXPRESS
            ),
            prefillData = PrefillDataHelper.prefillData.copy(
                billingFormAddress = PrefillDataHelper.prefillData.billingFormAddress?.copy(
                    name = "Test name"
                )
            )
        )
    )

    NavHost(navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) { HomeScreen(navController) }
        composable(route = Screen.CVVTokenization.route) {
            CVVTokenizationScreen(navController, createMediator(context))
        }
        composable(route = Screen.DefaultUI.route) { defaultPaymentFormMediator.PaymentForm() }
        composable(route = Screen.CustomThemingUI.route) { customThemingPaymentFormMediator.PaymentForm() }
        composable(route = Screen.CustomUI.route) { customPaymentFormMediator.PaymentForm() }
    }
}

fun createMediator(context: Context): CVVComponentMediator {
    val cvvComponentApi = CVVComponentApiFactory.create(PUBLIC_KEY, Environment.SANDBOX, context)

    val cvvComponentConfig = CVVComponentConfig(
        cardScheme = CardScheme.fromString(cardSchemeValue = "Visa"),
        onCVVValueChange = { isValidCVV ->
            println(isValidCVV)
        }
    )

    return cvvComponentApi.createComponentMediator(cvvComponentConfig)
}
