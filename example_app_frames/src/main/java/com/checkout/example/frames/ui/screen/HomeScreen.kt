package com.checkout.example.frames.ui.screen

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.checkout.CheckoutApiServiceFactory
import com.checkout.base.model.CardScheme
import com.checkout.base.model.Environment
import com.checkout.example.frames.ui.utils.PromptUtils
import com.checkout.example.frames.ui.utils.PromptUtils.neutralButton
import com.checkout.example.frames.navigation.Screen
import com.checkout.example.frames.ui.component.ButtonComponent
import com.checkout.example.frames.ui.component.TextComponent
import com.checkout.example.frames.ui.component.ThreedComponent
import com.checkout.example.frames.ui.theme.ButtonBorder
import com.checkout.example.frames.ui.theme.DarkBlue
import com.checkout.example.frames.ui.theme.FramesTheme
import com.checkout.example.frames.ui.theme.GrayColor
import com.checkout.example.frames.ui.utils.PUBLIC_KEY
import com.checkout.frames.R
import com.checkout.frames.cvvcomponent.CVVComponentApiFactory
import com.checkout.frames.cvvcomponent.models.CVVComponentConfig
import com.checkout.tokenization.model.GooglePayTokenRequest

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("MagicNumber", "LongMethod")
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    FramesTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Start
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logo),
                    modifier = Modifier
                        .padding(start = 6.dp, top = 16.dp)
                        .size(60.dp),
                    contentDescription = null
                )

                TextComponent(
                    titleResourceId = R.string.mobile_sdk,
                    fontSize = 24,
                    fontWeight = FontWeight.Bold,
                    paddingValues = PaddingValues(start = 24.dp),
                    textColor = Color(0XFF7F819A)
                )

                TextComponent(
                    titleResourceId = R.string.sample_app,
                    fontSize = 24,
                    fontWeight = FontWeight.Bold,
                    paddingValues = PaddingValues(start = 24.dp),
                    textColor = DarkBlue
                )

                Spacer(Modifier.height(35.dp))

                TextComponent(
                    titleResourceId = R.string.payment_experience,
                    fontSize = 20,
                    fontWeight = FontWeight.SemiBold,
                    paddingValues = PaddingValues(start = 24.dp, bottom = 14.dp),
                    textColor = DarkBlue
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, bottom = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ButtonComponent(
                        buttonTitleId = R.string.default_ui,
                        imageResourceID = R.drawable.ic_ui_type,
                        Modifier
                            .weight(1F)
                            .size(width = Dp.Unspecified, height = 80.dp)
                    ) {
                        navController.navigate(Screen.DefaultUI.route)
                    }
                    ButtonComponent(
                        buttonTitleId = R.string.custom_ui_theme,
                        imageResourceID = R.drawable.ic_theme_ui,
                        Modifier
                            .weight(1F)
                            .padding(start = 8.dp)
                            .size(width = Dp.Unspecified, height = 80.dp)
                    ) {
                        navController.navigate(Screen.CustomThemingUI.route)
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ButtonComponent(
                        buttonTitleId = R.string.custom_ui,
                        imageResourceID = R.drawable.ic_ui_type,
                        Modifier.size(width = Dp.Unspecified, height = 80.dp)
                    ) {
                        navController.navigate(Screen.CustomUI.route)
                    }
                }

                Spacer(Modifier.height(35.dp))

                TextComponent(
                    titleResourceId = R.string.cvv_component,
                    fontSize = 20,
                    fontWeight = FontWeight.SemiBold,
                    paddingValues = PaddingValues(start = 24.dp, bottom = 14.dp),
                    textColor = DarkBlue
                )
                val cvvComponentApi = CVVComponentApiFactory.create(PUBLIC_KEY, Environment.SANDBOX, context)

                val cvvComponentConfig = CVVComponentConfig(
                    CardScheme.fetchCardScheme(cardSchemeValue = "Visa"), onCVVValueChange = { isValidCVV ->
                        println(isValidCVV)
                    }
                )
                val mediator = cvvComponentApi.createComponentMediator(cvvComponentConfig)

                mediator.CVVComponent()

                Spacer(Modifier.height(35.dp))

                TextComponent(
                    titleResourceId = R.string.threed_secure,
                    fontSize = 20,
                    fontWeight = FontWeight.SemiBold,
                    paddingValues = PaddingValues(start = 24.dp, bottom = 14.dp),
                    textColor = DarkBlue
                )

                ThreedComponent(context)

                TextComponent(
                    titleResourceId = R.string.simulate_wallet,
                    fontSize = 20,
                    fontWeight = FontWeight.SemiBold,
                    paddingValues = PaddingValues(start = 24.dp, bottom = 14.dp),
                    textColor = DarkBlue
                )

                Card(
                    modifier = Modifier
                        .size(width = 190.dp, height = Dp.Unspecified)
                        .padding(start = 24.dp)
                        .align(Start)
                        .clickable { invokeCheckoutSDKToGenerateTokenForGooglePay(context) },
                    shape = RoundedCornerShape(12),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent, contentColor = DarkBlue),
                    border = BorderStroke(width = 1.dp, color = ButtonBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(start = 20.dp, top = 10.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_gpay),
                            modifier = Modifier.size(50.dp),
                            contentScale = ContentScale.Inside,
                            alignment = Alignment.Center,
                            contentDescription = null
                        )
                        TextComponent(
                            titleResourceId = R.string.google_pay,
                            fontSize = 18,
                            fontWeight = FontWeight.SemiBold,
                            paddingValues = PaddingValues(bottom = 6.dp, top = 10.dp),
                            textColor = DarkBlue
                        )
                        TextComponent(
                            titleResourceId = R.string.payment_data,
                            fontSize = 16,
                            fontWeight = FontWeight.Normal,
                            paddingValues = PaddingValues(bottom = 30.dp),
                            textColor = GrayColor
                        )
                    }
                }
            }
        }
    }
}

fun invokeCheckoutSDKToGenerateTokenForGooglePay(context: Context) {
    /**
     * Creating instance of CheckoutApiClient
     */
    val checkoutApiClient = CheckoutApiServiceFactory.create(
        "pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73", Environment.SANDBOX, context
    )

    /**
     * Invoke createToken method from checkoutApiClient
     * Pass tokenJsonPayload as parameter. The JSON string generated by GooglePay into [GooglePayTokenRequest]
     */
    checkoutApiClient.createToken(
        GooglePayTokenRequest(
            "{" + "\t\"protocolVersion\": \"ECv1\",\n" + "\t\"signature\":" +
                    "\"MEYCIQDRBIlMOzMjCEduZ6ENicfHlVx8679owbXV0lWbJ7pKDAIhAMEKPvzx8AlW6zxFNePoQMHCXjsHe" +
                    "PAhxnZwPkVywI2I\"," +
                    "\t\"signedMessage\": \"{\\\"encryptedMessage\\\":" +
                    "\\\"/rICAjT7ge2qvw0BU86Kt/v/5nLMiMdDhx6EXRRpKPvZGfZThR7FADqfrPvVJ0eStDTwD1v" +
                    "Xq+l+sXhBrQ+EdpHSv2oow3nQZeYwNU+nofKxfqSIDJBPgTMemphcBFkRsCdyDtOTI6AtVM7mxFm/QOiMzX0MdRNBrpLJp9" +
                    "cfQcxVX7O3z5IjG0t50dy4XtJPrdAd7N+0XG/KbrY466iNFPQuRYET65H4jXLGFK0RN+EQYe4gVAib91Y9KFenCh0x94V" +
                    "hoq4ayd6PCOL7Apj4G+yceDOoL5OAseGYk2JGwlW1DVLOj0YLUVBsWh86n0mRCARcgI5IuV6acHVL9XzEJQY/Z98oVSyVTo/" +
                    "PbeQKeaWR/qjcI9bdbpa9XIrYBCiKXrIau/mU2zg/Bq+OMrVJhRuFWFK9HMgxol0U+zNUVA9tOY4/QcHAEmfJgA2bhPCQQz" +
                    "qHYTn6g5Mv4vqCeV+k0MMcauinrDeJDgrFWKc2+Hu26uImWVk4\\\",\\\"ephemeralPublicKey\\\":\\\"BPPQFAG" +
                    "zdzp/TeDiBNABnju6FsL0lTNqKZTpUhzcyeWaK8XV19gtf1zhVAiZjOrLVGj3txoge9fW+x8bBghnFQc\\\\u003d\\\"," +
                    "\\\"tag\\\":\\\"Ewh1yD3bR5wqTRlEV6TPQpvWaUSBFTMsCAILpGejmtI\\\\u003d\\\"}\"\n" +
                    "}",
            onSuccess = {
                showAlertDialog(context, context.getString(R.string.token_generated), it.token)
            }, onFailure = {
                showAlertDialog(context, context.getString(R.string.token_generated_failed), it)
            }
        )
    )
}

fun showAlertDialog(context: Context, title: String, message: String) {
    PromptUtils.alertDialog(context) {
        setTitle(title)
        setMessage(message)
        neutralButton {
            it.dismiss()
        }
    }.show()
}
