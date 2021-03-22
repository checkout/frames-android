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

package com.google.android.gms.samples.wallet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.checkout.android_sdk.CheckoutAPIClient;
import com.checkout.android_sdk.Response.GooglePayTokenisationFail;
import com.checkout.android_sdk.Response.GooglePayTokenisationResponse;
import com.checkout.android_sdk.Utils.Environment;
import com.checkout.android_sdk.network.NetworkError;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentMethodToken;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.TransactionInfo;

public class CheckoutActivity extends Activity {

    CheckoutAPIClient mCheckoutAPIClient;

    CheckoutAPIClient.OnGooglePayTokenGenerated mGooglePayListener =
            new CheckoutAPIClient.OnGooglePayTokenGenerated() {
                @Override
                public void onTokenGenerated(GooglePayTokenisationResponse response) {
                    displayMessage("Success", response.getToken());
                }

                @Override
                public void onError(GooglePayTokenisationFail error) {
                    displayMessage("Error", error.getErrorType());
                }

                @Override
                public void onNetworkError(NetworkError error) {
                    displayMessage("NetworkError", Log.getStackTraceString(error));
                }
            };

    // Arbitrarily-picked result code.
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;

    private PaymentsClient mPaymentsClient;

    private View mGooglePayButton;
    private TextView mGooglePayStatusText;

    private ItemInfo mBikeItem = new ItemInfo("Simple Bike", 300 * 1000000, R.drawable.bike);
    private long mShippingCost = 90 * 1000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Set up the mock information for our item in the UI.
        initItemUI();

        mGooglePayButton = findViewById(R.id.googlepay_button);
        mGooglePayStatusText = findViewById(R.id.googlepay_status);

        mGooglePayButton.setOnClickListener(this::requestPayment);

        // It's recommended to create the PaymentsClient object inside of the onCreate method.
        mPaymentsClient = PaymentsUtil.createPaymentsClient(this);
        checkIsReadyToPay();
    }

    private void checkIsReadyToPay() {
        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        PaymentsUtil.isReadyToPay(mPaymentsClient).addOnCompleteListener(
                task -> {
                    try {
                        boolean result = task.getResult(ApiException.class);
                        setGooglePayAvailable(result);
                    } catch (ApiException exception) {
                        // Process error
                        Log.w("isReadyToPay failed", exception);
                    }
                });
    }

    private void setGooglePayAvailable(boolean available) {
        // If isReadyToPay returned true, show the button and hide the "checking" text. Otherwise,
        // notify the user that Pay with Google is not available.
        // Please adjust to fit in with your current user flow. You are not required to explicitly
        // let the user know if isReadyToPay returns false.
        if (available) {
            mGooglePayStatusText.setVisibility(View.GONE);
            mGooglePayButton.setVisibility(View.VISIBLE);
        } else {
            mGooglePayStatusText.setText(R.string.googlepay_status_unavailable);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        handlePaymentSuccess(paymentData);
                        break;
                    case Activity.RESULT_CANCELED:
                        // Nothing to here normally - the user simply cancelled without selecting a
                        // payment method.
                        break;
                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        handleError(status.getStatusCode());
                        break;
                }

                // Re-enables the Pay with Google button.
                mGooglePayButton.setClickable(true);
                break;
        }
    }

    private void handlePaymentSuccess(@Nullable PaymentData paymentData) {
        // PaymentMethodToken contains the payment information, as well as any additional
        // requested information, such as billing and shipping address.
        //
        // Refer to your processor's documentation on how to proceed from here.

        // getPaymentMethodToken will only return null if PaymentMethodTokenizationParameters was
        // not set in the PaymentRequest.
        if (paymentData != null && paymentData.getPaymentMethodToken() != null) {
            PaymentMethodToken token = paymentData.getPaymentMethodToken();
            // Use token.getToken() to get the token string.
            Log.d("PaymentData", "PaymentMethodToken received");

            // If the gateway is set to example, no payment information is returned - instead, the
            // token will only consist of "examplePaymentMethodToken".
            mCheckoutAPIClient = new CheckoutAPIClient(
                    this,             // activity context
                    "pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73",          // your public key
                    Environment.SANDBOX  // the environment
            );
            mCheckoutAPIClient.setGooglePayListener(mGooglePayListener); // pass the callback

            try {
                mCheckoutAPIClient.generateGooglePayToken(token.getToken()); // the payload is the JSON string generated by GooglePay
            } catch (Exception e) {
                displayMessage("Exception", Log.getStackTraceString(e));
            }
        }
    }

    private void handleError(int statusCode) {
        // At this stage, the user has already seen a popup informing them an error occurred.
        // Normally, only logging is required.
        // statusCode will hold the value of any constant from CommonStatusCode or one of the
        // WalletConstants.ERROR_CODE_* constants.
        Log.w("loadPaymentData failed", String.format("Error code: %d", statusCode));
    }

    // This method is called when the Pay with Google button is clicked.
    public void requestPayment(View view) {
        // Disables the button to prevent multiple clicks.
        mGooglePayButton.setClickable(false);

        // The price provided to the API should include taxes and shipping.
        // This price is not displayed to the user.
        String price = PaymentsUtil.microsToString(mBikeItem.getPriceMicros() + mShippingCost);

        TransactionInfo transaction = PaymentsUtil.createTransaction(price);
        PaymentDataRequest request = PaymentsUtil.createPaymentDataRequest(transaction);
        Task<PaymentData> futurePaymentData = mPaymentsClient.loadPaymentData(request);

        // Since loadPaymentData may show the UI asking the user to select a payment method, we use
        // AutoResolveHelper to wait for the user interacting with it. Once completed,
        // onActivityResult will be called with the result.
        AutoResolveHelper.resolveTask(futurePaymentData, this, LOAD_PAYMENT_DATA_REQUEST_CODE);
    }

    private void initItemUI() {
        TextView itemName = findViewById(R.id.text_item_name);
        ImageView itemImage = findViewById(R.id.image_item_image);
        TextView itemPrice = findViewById(R.id.text_item_price);

        itemName.setText(mBikeItem.getName());
        itemImage.setImageResource(mBikeItem.getImageResourceId());
        itemPrice.setText(PaymentsUtil.microsToString(mBikeItem.getPriceMicros()));
    }

    private void displayMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> { /* Do things */});
        AlertDialog alert = builder.create();
        alert.show();
    }
}
