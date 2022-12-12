package com.checkout.example.frames.ui.screen

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.checkout.CheckoutApiServiceFactory
import com.checkout.base.model.Environment
import com.checkout.example.frames.ui.utils.FAILURE_URL
import com.checkout.example.frames.ui.utils.PUBLIC_KEY
import com.checkout.example.frames.ui.utils.PromptUtils
import com.checkout.example.frames.ui.utils.PromptUtils.neutralButton
import com.checkout.example.frames.ui.utils.SUCCESS_URL
import com.checkout.example.frames.ui.utils.URL_IDENTIFIER
import com.checkout.frames.R
import com.checkout.threedsecure.model.ThreeDSRequest
import com.checkout.threedsecure.model.ThreeDSResult
import com.checkout.threedsecure.model.ThreeDSResultHandler

class ThreedSecureActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val url = intent.getStringExtra(URL_IDENTIFIER)
            invokeThreedSecureFlow(LocalContext.current, url ?: "", findViewById(android.R.id.content))
        }
    }

    private fun showAlertDialog(componentActivity: ComponentActivity, title: String, message: String) {
        PromptUtils.alertDialog(componentActivity) {
            setTitle(title)
            setMessage(message)
            neutralButton {
                it.dismiss()
                componentActivity.finish()
            }
        }.show()
    }

    private fun invokeThreedSecureFlow(context: Context, url: String, viewGroup: ViewGroup) {
        /**
         * Creating instance of CheckoutApiClient
         */
        val checkoutApiClient = CheckoutApiServiceFactory.create(
            PUBLIC_KEY, Environment.SANDBOX, context
        )

        val request = ThreeDSRequest(
            container = viewGroup, // Provide a ViewGroup container for 3DS WebView
            challengeUrl = url, // Provide a 3D Secure URL
            successUrl = SUCCESS_URL, failureUrl = FAILURE_URL, resultHandler = threeDSResultHandler
        )

        /**
         * Invoke handleThreeDS method from checkoutApiClient
         * Pass request as parameter.
         */
        checkoutApiClient.handleThreeDS(request)
    }

    private val threeDSResultHandler: ThreeDSResultHandler = { threeDSResult: ThreeDSResult ->
        when (threeDSResult) {
            // Used when [ProcessThreeDSRequest.redirectUrl] contains [ThreeDSRequest.successUrl]
            is ThreeDSResult.Success -> {
                showAlertDialog(this, getString(R.string.authentication_success), threeDSResult.token)
            }
            // Used when [ProcessThreeDSRequest.redirectUrl] contains [ThreeDSRequest.successUrl]
            is ThreeDSResult.Failure -> { /* Handle failure result */
                showAlertDialog(this, this.getString(R.string.token_generated_failed), "")
            }
            // Used when [ProcessThreeDSRequest.redirectUrl] can't be parsed or when error received from 3DS WebView
            is ThreeDSResult.Error -> { /* Handle success result */
                showAlertDialog(
                    this, this.getString(R.string.token_generated_failed), threeDSResult.error.message.toString()
                )
            }
        }
    }
}
