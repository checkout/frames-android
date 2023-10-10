package com.checkout.example.frames.ui.screen

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.checkout.example.frames.ui.utils.ENVIRONMENT
import com.checkout.example.frames.ui.utils.FAILURE_URL
import com.checkout.example.frames.ui.utils.PUBLIC_KEY
import com.checkout.example.frames.ui.utils.PromptUtils
import com.checkout.example.frames.ui.utils.PromptUtils.neutralButton
import com.checkout.example.frames.ui.utils.SUCCESS_URL
import com.checkout.example.frames.ui.utils.URL_IDENTIFIER
import com.checkout.frames.R
import com.checkout.frames.api.PaymentFlowHandler
import com.checkout.frames.api.PaymentFormMediator
import com.checkout.frames.screen.paymentform.model.PaymentFormConfig
import com.checkout.frames.style.screen.PaymentFormStyle
import com.checkout.threedsecure.model.ThreeDSRequest
import com.checkout.threedsecure.model.ThreeDSResult
import com.checkout.threedsecure.model.ThreeDSResultHandler
import com.checkout.tokenization.model.TokenDetails

class ThreedSecureActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val url = intent.getStringExtra(URL_IDENTIFIER)

            val paymentFormConfig = PaymentFormConfig(
                publicKey = PUBLIC_KEY,
                context = this,
                environment = ENVIRONMENT,
                paymentFlowHandler = object : PaymentFlowHandler {
                    override fun onSubmit() {
                        /*Intentionally left empty*/
                    }

                    override fun onSuccess(tokenDetails: TokenDetails) {
                        /*Intentionally left empty*/
                    }

                    override fun onFailure(errorMessage: String) {
                        /*Intentionally left empty*/
                    }

                    override fun onBackPressed() { finish() }
                },
                style = PaymentFormStyle(),
            )
            val paymentFormMediator = PaymentFormMediator(paymentFormConfig)
            invokeThreedSecureFlow(url ?: "", findViewById(android.R.id.content), paymentFormMediator)
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

    private fun invokeThreedSecureFlow(url: String, viewGroup: ViewGroup, paymentFormMediator: PaymentFormMediator) {
        val request = ThreeDSRequest(
            container = viewGroup, // Provide a ViewGroup container for 3DS WebView
            challengeUrl = url, // Provide a 3D Secure URL
            successUrl = SUCCESS_URL,
            failureUrl = FAILURE_URL,
            resultHandler = threeDSResultHandler,
        )

        /**
         * Invoke handleThreeDS method from paymentFormMediator
         * Pass request as parameter.
         */
        paymentFormMediator.handleThreeDS(request)
    }

    private val threeDSResultHandler: ThreeDSResultHandler = { threeDSResult: ThreeDSResult ->
        when (threeDSResult) {
            // Used when [ProcessThreeDSRequest.redirectUrl] contains [ThreeDSRequest.successUrl]
            is ThreeDSResult.Success -> {
                showAlertDialog(this, getString(R.string.authentication_success), threeDSResult.token)
            }
            // Used when [ProcessThreeDSRequest.redirectUrl] contains [ThreeDSRequest.successUrl]
            is ThreeDSResult.Failure -> {
                /* Handle failure result */
                showAlertDialog(this, this.getString(R.string.token_generated_failed), "")
            }
            // Used when [ProcessThreeDSRequest.redirectUrl] can't be parsed or when error received from 3DS WebView
            is ThreeDSResult.Error -> {
                /* Handle success result */
                showAlertDialog(
                    this,
                    this.getString(R.string.token_generated_failed),
                    threeDSResult.error.message.toString(),
                )
            }
        }
    }
}
