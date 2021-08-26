package checkout.checkout_android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.checkout.android_sdk.PaymentForm;
import com.checkout.android_sdk.PaymentForm.On3DSFinished;
import com.checkout.android_sdk.PaymentForm.PaymentFormCallback;
import com.checkout.android_sdk.Response.CardTokenisationFail;
import com.checkout.android_sdk.Response.CardTokenisationResponse;
import com.checkout.android_sdk.network.NetworkError;

import java.util.Locale;

import checkout.checkout_android.utils.PaymentUtil;

public class DemoActivity extends Activity {

    private PaymentForm mPaymentForm;
    private ProgressDialog mProgressDialog;

    // Callback used for the Payment Form interaction
    private final PaymentFormCallback mFormListener = new PaymentFormCallback() {
        @Override
        public void onFormSubmit() {
            mProgressDialog.show(); // show loader
        }

        @Override
        public void onTokenGenerated(CardTokenisationResponse response) {
            mProgressDialog.dismiss(); // dismiss the loader
            mPaymentForm.clearForm(); // clear the form
            displayMakePaymentMessage(response.getToken());
        }

        @Override
        public void onError(CardTokenisationFail response) {
            mProgressDialog.dismiss(); // dismiss the loader
            displayMessage("Token Error", response.getErrorType(), false);
        }

        @Override
        public void onNetworkError(NetworkError error) {
            mProgressDialog.dismiss(); // dismiss the loader
            displayMessage("Network Error", String.valueOf(error), false);
        }

        @Override
        public void onBackPressed() {
            finish();
        }
    };

    private final On3DSFinished m3DSecureListener = new On3DSFinished() {
        @Override
        public void onSuccess(String token) {
            displayMessage("Result", "Authentication success: " + token, true);
        }

        @Override
        public void onError(String errorMessage) {
            displayMessage("Result", "Authentication failure: " + errorMessage, true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        // initialise the loader
        mProgressDialog = new ProgressDialog(DemoActivity.this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Loading...");

        mPaymentForm = findViewById(R.id.checkout_card_form);
        mPaymentForm
                .setFormListener(mFormListener)
                .set3DSListener(m3DSecureListener)
                .setEnvironment(Constants.ENVIRONMENT)
                .setKey(Constants.PUBLIC_KEY)
                .setDefaultBillingCountry(Locale.UK);

        if (savedInstanceState == null) {
            mPaymentForm.clearForm();
        }
    }

    private void displayMessage(String title, String message, boolean exitScreen) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> {
                    if (exitScreen) finish();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void displayMakePaymentMessage(final String token) {
        StringBuilder message = new StringBuilder();
        message.append("Card Token: ").append(token);
        message.append("\n\n");
        message.append("Purchase using this card with 3DS?");

        new AlertDialog.Builder(this)
                .setTitle("Token Created")
                .setMessage(message)
                .setCancelable(false)
                .setNeutralButton("Yes", (dialog, id) -> purchase(token))
                .setNegativeButton("No", (dialog, which) -> finish())
                .show();
    }


    private void purchase(String token) {
        mProgressDialog.show();
        PaymentUtil.createPayment(token, (success, redirectUrl) -> {
            mProgressDialog.dismiss();
            if (redirectUrl != null) {
                start3DS(redirectUrl);
            } else {
                displayMessage("Payment", success ? "Payment Success" : "Payment Failed", false);
            }
        });
    }

    private void start3DS(@NonNull String redirectUrl) {
//        mPaymentForm.handle3DS(
//                redirectUrl,
//                Constants.SUCCESS_URL,
//                Constants.FAILURE_URL
//        );

//        CustomTabManager customTabManager = new CustomTabManager(this);
//        customTabManager.navigateToUrl(redirectUrl, null);

        Intent authenticationActivity = new Intent(this, AuthenticationActivity.class);
        authenticationActivity.putExtra(AuthenticationActivity.EXTRA_AUTH_URL, redirectUrl);
        startActivityForResult(authenticationActivity, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            String resultString;
            switch (resultCode) {
                case AuthenticationActivity.RESULT_AUTH_SUCCESS: resultString = "Success"; break;
                case AuthenticationActivity.RESULT_AUTH_FAILED: resultString = "Failed"; break;
                default: resultString = "Unknown"; break;
            }
            displayMessage("Result", "Custom Tab Authentication " + resultString, true);
        }
    }
}