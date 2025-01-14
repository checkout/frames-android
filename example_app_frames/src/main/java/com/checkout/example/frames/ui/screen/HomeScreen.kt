package com.checkout.example.frames.ui.screen

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.checkout.base.model.Environment
import com.checkout.example.frames.R
import com.checkout.example.frames.navigation.Screen
import com.checkout.example.frames.ui.component.ButtonComponent
import com.checkout.example.frames.ui.component.TextComponent
import com.checkout.example.frames.ui.component.ThreedComponent
import com.checkout.example.frames.ui.theme.ButtonBorder
import com.checkout.example.frames.ui.theme.DarkBlue
import com.checkout.example.frames.ui.theme.FramesTheme
import com.checkout.example.frames.ui.theme.GrayColor
import com.checkout.example.frames.ui.utils.PromptUtils
import com.checkout.example.frames.ui.utils.PromptUtils.neutralButton
import com.checkout.tokenization.model.GooglePayTokenRequest

@Suppress("MagicNumber", "LongMethod")
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    FramesTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Start,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logo),
                    modifier = Modifier
                        .padding(start = 6.dp, top = 16.dp)
                        .size(60.dp),
                    contentDescription = null,
                )

                TextComponent(
                    titleResourceId = R.string.mobile_sdk,
                    fontSize = 24,
                    fontWeight = FontWeight.Bold,
                    paddingValues = PaddingValues(start = 24.dp),
                    textColor = Color(0XFF7F819A),
                )

                TextComponent(
                    titleResourceId = R.string.sample_app,
                    fontSize = 24,
                    fontWeight = FontWeight.Bold,
                    paddingValues = PaddingValues(start = 24.dp),
                    textColor = DarkBlue,
                )

                Spacer(Modifier.height(35.dp))

                TextComponent(
                    titleResourceId = R.string.payment_experience,
                    fontSize = 20,
                    fontWeight = FontWeight.SemiBold,
                    paddingValues = PaddingValues(start = 24.dp, bottom = 14.dp),
                    textColor = DarkBlue,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, bottom = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    ButtonComponent(
                        buttonTitleId = R.string.default_ui,
                        imageResourceID = R.drawable.ic_ui_type,
                        Modifier
                            .weight(1F)
                            .size(width = Dp.Unspecified, height = 80.dp),
                    ) {
                        navController.navigate(Screen.DefaultUI.route)
                    }
                    ButtonComponent(
                        buttonTitleId = R.string.custom_ui_theme,
                        imageResourceID = R.drawable.ic_theme_ui,
                        Modifier
                            .weight(1F)
                            .padding(start = 8.dp)
                            .size(width = Dp.Unspecified, height = 80.dp),
                    ) {
                        navController.navigate(Screen.CustomThemingUI.route)
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, bottom = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    ButtonComponent(
                        buttonTitleId = R.string.custom_ui,
                        imageResourceID = R.drawable.ic_ui_type,
                        Modifier
                            .weight(1F)
                            .size(width = Dp.Unspecified, height = 80.dp),
                    ) {
                        navController.navigate(Screen.CustomUI.route)
                    }
                    ButtonComponent(
                        buttonTitleId = R.string.cvv_component,
                        imageResourceID = R.drawable.ic_ui_type,
                        Modifier
                            .weight(1F)
                            .padding(start = 8.dp)
                            .size(width = Dp.Unspecified, height = 80.dp),
                    ) {
                        navController.navigate(Screen.CVVTokenization.route)
                    }
                }

                Spacer(Modifier.height(35.dp))

                TextComponent(
                    titleResourceId = R.string.threed_secure,
                    fontSize = 20,
                    fontWeight = FontWeight.SemiBold,
                    paddingValues = PaddingValues(start = 24.dp, bottom = 14.dp),
                    textColor = DarkBlue,
                )

                ThreedComponent(context)

                TextComponent(
                    titleResourceId = R.string.simulate_wallet,
                    fontSize = 20,
                    fontWeight = FontWeight.SemiBold,
                    paddingValues = PaddingValues(start = 24.dp, bottom = 14.dp),
                    textColor = DarkBlue,
                )

                Card(
                    modifier = Modifier
                        .size(width = 190.dp, height = Dp.Unspecified)
                        .padding(start = 24.dp)
                        .align(Start)
                        .clickable { invokeCheckoutSDKToGenerateTokenForGooglePay(context) },
                    shape = RoundedCornerShape(12),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent, contentColor = DarkBlue),
                    border = BorderStroke(width = 1.dp, color = ButtonBorder),
                ) {
                    Column(
                        modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_gpay),
                            modifier = Modifier.size(50.dp),
                            contentScale = ContentScale.Inside,
                            alignment = Alignment.Center,
                            contentDescription = null,
                        )
                        TextComponent(
                            titleResourceId = R.string.google_pay,
                            fontSize = 18,
                            fontWeight = FontWeight.SemiBold,
                            paddingValues = PaddingValues(bottom = 6.dp, top = 10.dp),
                            textColor = DarkBlue,
                        )
                        TextComponent(
                            titleResourceId = R.string.payment_data,
                            fontSize = 16,
                            fontWeight = FontWeight.Normal,
                            paddingValues = PaddingValues(bottom = 30.dp),
                            textColor = GrayColor,
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
        "pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73",
        Environment.SANDBOX,
        context,
    )

    /**
     * Invoke createToken method from checkoutApiClient Pass tokenJsonPayload as parameter. The JSON string generated by
     * GooglePay into [GooglePayTokenRequest]
     */
    checkoutApiClient.createToken(
        GooglePayTokenRequest(
            "{\"signature\":\"MEUCIBBkOdSeFLTyhtwGeBjyjUOGfY9w46ulDL/xIcQVsGPMAiEA+kcc7+" +
                "yGgkpWRcT7QtDdvsi//BVGhboYagUyHlp+7eE\\u003d\",\"protocolVersion\":\"ECv1\",\"signedMessage\":\"" +
                "{\\\"encryptedMessage\\\":\\\"Es/MqYVGHX/VltWkU8bdDE/xKCYMfyeBHmCuJojEzZC9JWM57BwoYf0H+9wb0ePmLuRZa5" +
                "ZP+b2svI7Oy0WJjFgndIdsB2p0bxwE0JtrjYamHP51Aj8oPakxRsUN4YSfDukRPq2mWRNONJjN8o/6jvAW/710o9+JZ0PdHYnW/" +
                "a8WR98Joh0AOMDTlyLOOHFsH5FkwoUCJlv4vcWxIE6ZQc4W/bQ/OGZ9B05kbFlOlLjmB4nDReqTvh1Vnv+v9B+C/OZg084OiC" +
                "aAdjLZJeqqaZteVM3CSIIRly/1AMs+uCtPlBligTONqNXfhqce1LwY98y3H4l+Ef1eM4EmUjkCkvSN+sEaEn3btFOrBX7MQ/UU" +
                "PGN8EgPmQBpRs3opqVwNFHwgsT+3eeags1NLGA7jSaI5ODXH8zl6KSIJsSFglhG2IT6axC8g6t0WsjxdQjMnqERYPQlQ4K" +
                "ka\\\",\\\"ephemeralPublicKey\\\":\\\"BGRaMmC+vqUGkEy9wvPwp2Kp31bHJWjgtWJxa2v9Uwc2a6nOQcVthjcpcSsS" +
                "h8C7dARuC6FalhHFXSss4G9uJus\\\\u003d\\\",\\\"tag\\\":\\\"aNjQvDRbEBHk65J6TkXx0BrsHGCLmK3sTLDh/zuGu" +
                "gE\\\\u003d\\\"}\"}",
            onSuccess = {
                showAlertDialog(context, context.getString(R.string.token_generated), it.token)
            },
            onFailure = {
                showAlertDialog(context, context.getString(R.string.token_generated_failed), it)
            },
        ),
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
