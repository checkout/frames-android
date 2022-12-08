package com.checkout.example.frames.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.checkout.CheckoutApiServiceFactory
import com.checkout.base.model.Environment
import com.checkout.example.frames.ui.utils.PromptUtils
import com.checkout.example.frames.ui.utils.PromptUtils.neutralButton
import com.checkout.example.frames.navigation.Screen
import com.checkout.example.frames.ui.theme.DarkBlue
import com.checkout.example.frames.ui.theme.FramesTheme
import com.checkout.frames.R
import com.checkout.tokenization.model.GooglePayTokenRequest

@Suppress("MagicNumber", "LongMethod")
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    FramesTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxSize(),
                Arrangement.Top,
                Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = null
                )

                Text(
                    text = stringResource(R.string.payment_experience),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                Button(
                    onClick = { navController.navigate(Screen.DefaultUI.route) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20),
                    colors = ButtonDefaults.buttonColors(DarkBlue)
                ) {
                    Text(
                        text = stringResource(id = R.string.default_ui),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                Spacer(Modifier.height(25.dp))

                Button(
                    onClick = { navController.navigate(Screen.CustomThemingUI.route) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20),
                    colors = ButtonDefaults.buttonColors(DarkBlue)
                ) {
                    Text(
                        text = stringResource(id = R.string.custom_ui_theme),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                Spacer(Modifier.height(25.dp))

                Button(
                    onClick = { navController.navigate(Screen.CustomUI.route) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20),
                    colors = ButtonDefaults.buttonColors(DarkBlue)
                ) {
                    Text(
                        text = stringResource(id = R.string.custom_ui),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                Spacer(Modifier.height(30.dp))

                Text(
                    text = stringResource(R.string.generate_token),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                Button(
                    onClick = { invokeCheckoutSDKToGenerateTokenForGooglePay(context) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20),
                    colors = ButtonDefaults.buttonColors(DarkBlue)
                ) {
                    Text(
                        text = stringResource(id = R.string.google_pay_payment_data),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}

fun invokeCheckoutSDKToGenerateTokenForGooglePay(context: Context) {
    /**
     * Creating instance of CheckoutApiClient
     */
    val checkoutApiClient =
        CheckoutApiServiceFactory.create(
            "pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73",
            Environment.SANDBOX,
            context
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
