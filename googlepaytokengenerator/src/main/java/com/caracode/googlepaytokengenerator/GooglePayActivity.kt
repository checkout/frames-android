/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.caracode.googlepaytokengenerator

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.checkout.sdk.CheckoutClient
import com.checkout.sdk.core.RequestMaker
import com.checkout.sdk.core.TokenResult
import com.checkout.sdk.request.GooglePayTokenizationRequest
import com.checkout.sdk.utils.Environment
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.samples.wallet.PaymentsUtil
import com.google.android.gms.samples.wallet.microsToString
import com.google.android.gms.wallet.*
import kotlinx.android.synthetic.main.activity_checkout.*
import org.json.JSONException
import org.json.JSONObject


class GooglePayActivity : Activity() {

    /**
     * A client for interacting with the Google Pay API.
     *
     * @see [PaymentsClient](https://developers.google.com/android/reference/com/google/android/gms/wallet/PaymentsClient)
     */
    private val TAG = GooglePayActivity::class.java.simpleName
    private lateinit var paymentsClient: PaymentsClient
    private val bikeItem = ItemInfo("Complex Bike", (1 * 1000000).toLong(), R.drawable.bike)
    private val shippingCost = (0 * 1000000).toLong()

    /**
     * Arbitrarily-picked constant integer you define to track a request for payment data activity.
     *
     * @value #LOAD_PAYMENT_DATA_REQUEST_CODE
     */
    private val LOAD_PAYMENT_DATA_REQUEST_CODE = 991

    /**
     * Initialize the Google Pay API on creation of the activity
     *
     * @see Activity.onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        // Set up the mock information for our item in the UI.
        initItemUI()

        // Initialize a Google Pay API client for an environment suitable for testing.
        // It's recommended to create the PaymentsClient object inside of the onCreate method.
        paymentsClient = PaymentsUtil.createPaymentsClient(this)
        possiblyShowGooglePayButton()

        googlePayButton.setOnClickListener { requestPayment() }
    }

    /**
     * Determine the viewer's ability to pay with a payment method supported by your app and display a
     * Google Pay payment button.
     *
     * @see [](https://developers.google.com/android/reference/com/google/android/gms/wallet/PaymentsClient.html.isReadyToPay
    ) */
    private fun possiblyShowGooglePayButton() {

        val isReadyToPayJson = PaymentsUtil.isReadyToPayRequest() ?: return
        val request = IsReadyToPayRequest.fromJson(isReadyToPayJson.toString()) ?: return

        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        val task = paymentsClient.isReadyToPay(request)
        task.addOnCompleteListener { completedTask ->
            try {
                completedTask.getResult(ApiException::class.java)?.let(::setGooglePayAvailable)
            } catch (exception: ApiException) {
                // Process error
                Log.w("isReadyToPay failed", exception)
            }
        }
    }

    /**
     * If isReadyToPay returned `true`, show the button and hide the "checking" text. Otherwise,
     * notify the user that Google Pay is not available. Please adjust to fit in with your current
     * user flow. You are not required to explicitly let the user know if isReadyToPay returns `false`.
     *
     * @param available isReadyToPay API response.
     */
    private fun setGooglePayAvailable(available: Boolean) {
        if (available) {
            googlePayStatusText.visibility = View.GONE
            googlePayButton.visibility = View.VISIBLE
        } else {
            googlePayStatusText.setText(R.string.googlepay_status_unavailable)
        }
    }

    /**
     * Handle a resolved activity from the Google Pay payment sheet.
     *
     * @param requestCode Request code originally supplied to AutoResolveHelper in requestPayment().
     * @param resultCode Result code returned by the Google Pay API.
     * @param data Intent from the Google Pay API containing payment or error data.
     * @see [Getting a result
     * from an Activity](https://developer.android.com/training/basics/intents/result)
     */
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            // value passed in AutoResolveHelper
            LOAD_PAYMENT_DATA_REQUEST_CODE -> {
                when (resultCode) {
                    Activity.RESULT_OK ->
                        if (data != null) {
                            PaymentData.getFromIntent(data)?.let(::handlePaymentSuccess)
                        }

                    Activity.RESULT_CANCELED -> {
                        // Nothing to do here normally - the user simply cancelled without selecting a
                        // payment method.
                    }

                    AutoResolveHelper.RESULT_ERROR -> {
                        AutoResolveHelper.getStatusFromIntent(data)?.let {
                            handleError(it.statusCode)
                        }
                    }
                }
                googlePayButton.isClickable = true
            }
        }
    }

    /**
     * PaymentData response object contains the payment information, as well as any additional
     * requested information, such as billing and shipping address.
     *
     * @param paymentData A response object returned by Google after a payer approves payment.
     * @see [Payment
     * Data](https://developers.google.com/pay/api/android/reference/object.PaymentData)
     */
    private fun handlePaymentSuccess(paymentData: PaymentData) {
        val paymentInformation = paymentData.toJson() ?: return

        try {
            // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
            val paymentMethodData =
                JSONObject(paymentInformation).getJSONObject("paymentMethodData")

            // If the gateway is set to "example", no payment information is returned - instead, the
            // token will only consist of "examplePaymentMethodToken".
            if (paymentMethodData
                    .getJSONObject("tokenizationData")
                    .getString("type") == "PAYMENT_GATEWAY" && paymentMethodData
                    .getJSONObject("tokenizationData")
                    .getString("token") == "examplePaymentMethodToken"
            ) {

                AlertDialog.Builder(this)
                    .setTitle("Warning")
                    .setMessage(
                        "Gateway name set to \"example\" - please modify " +
                                "Constants.java and replace it with your own gateway."
                    )
                    .setPositiveButton("OK", null)
                    .create()
                    .show()
            }

            val billingName = paymentMethodData.getJSONObject("info")
                .getJSONObject("billingAddress").getString("name")
            Log.d("BillingName", billingName)

            Toast.makeText(
                this,
                getString(R.string.payments_show_name, billingName),
                Toast.LENGTH_LONG
            ).show()

            // Logging token string.
            Log.d(
                "GooglePaymentToken", paymentMethodData
                    .getJSONObject("tokenizationData")
                    .getString("token")
            )

            sendGooglePayToken(paymentMethodData.getJSONObject("tokenizationData").getString("token"))

        } catch (e: JSONException) {
            Log.e("handlePaymentSuccess", "Error: " + e.toString())
        }

    }

    /**
     * At this stage, the user has already seen a popup informing them an error occurred. Normally,
     * only logging is required.
     *
     * @param statusCode will hold the value of any constant from CommonStatusCode or one of the
     * WalletConstants.ERROR_CODE_* constants.
     * @see [
     * Wallet Constants Library](https://developers.google.com/android/reference/com/google/android/gms/wallet/WalletConstants.constant-summary)
     */
    private fun handleError(statusCode: Int) {
        Log.w("loadPaymentData failed", String.format("Error code: %d", statusCode))
    }

    // This method is called when the Pay with Google button is clicked.
    private fun requestPayment() {

        // Disables the button to prevent multiple clicks.
        googlePayButton.isClickable = false

        // The price provided to the API should include taxes and shipping.
        // This price is not displayed to the user.
        val price = (bikeItem.priceMicros + shippingCost).microsToString()

        val paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(price)
        if (paymentDataRequestJson == null) {
            Log.e("RequestPayment", "Can't fetch payment data request")
            return
        }
        val request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())

        // Since loadPaymentData may show the UI asking the user to select a payment method, we use
        // AutoResolveHelper to wait for the user interacting with it. Once completed,
        // onActivityResult will be called with the result.
        if (request != null) {
            AutoResolveHelper.resolveTask(
                paymentsClient.loadPaymentData(request), this, LOAD_PAYMENT_DATA_REQUEST_CODE
            )
        }
    }

    private fun initItemUI() {
        itemName.text = bikeItem.name
        itemImage.setImageResource(bikeItem.imageResourceId)
        itemPrice.text = bikeItem.priceMicros.microsToString()
    }

    private fun sendGooglePayToken(jsonToken: String) {
        val googlePayToken = JSONObject(jsonToken)

        val client = createCheckoutClient(createTokenCallback())
        val requestMaker = createRequestMaker(client)
        val googlePayTokenizationRequest = GooglePayTokenizationRequest(
            googlePayToken.getString("protocolVersion"),
            googlePayToken.getString("signature"),
            googlePayToken.getString("signedMessage"))
        requestMaker.makeTokenRequest(googlePayTokenizationRequest)
    }

    private fun createTokenCallback(): CheckoutClient.TokenCallback {
        return CheckoutClient.TokenCallback { tokenResult ->
            when (tokenResult) {
                is TokenResult.TokenResultSuccess -> googleTokenResult.text = "Token: ${tokenResult.response.token()}"
                is TokenResult.TokenResultTokenizationFail -> googleTokenResult.text = "Error: ${tokenResult.error.errorCode()}"
                is TokenResult.TokenResultNetworkError -> googleTokenResult.text = "Network Error: ${tokenResult.exception.javaClass.simpleName}"
            }
        }
    }

    private fun createRequestMaker(checkoutClient: CheckoutClient): RequestMaker {
        return RequestMaker.create(this, checkoutClient)
    }

    private fun createCheckoutClient(tokenCallback: CheckoutClient.TokenCallback): CheckoutClient {
        return CheckoutClient(this, "pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73", Environment.SANDBOX, tokenCallback)
    }
}
