package checkout.checkout_android;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;

import com.checkout.frames.api.PaymentFormMediator;
import com.checkout.frames.paymentflow.PaymentFlowHandler;
import com.checkout.frames.screen.paymentform.PaymentFormConfig;
import com.checkout.frames.style.screen.PaymentFormStyle;
import com.checkout.threedsecure.model.ThreeDSRequest;
import com.checkout.threedsecure.model.ThreeDSResult;
import com.checkout.tokenization.model.TokenDetails;

import checkout.checkout_android.utils.PaymentUtil;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class DemoActivity extends ComponentActivity {

    private ProgressDialog mProgressDialog;
    private PaymentFormMediator mPaymentFormMediator;

    private final PaymentFlowHandler mPaymentFlowHandler = new PaymentFlowHandler() {

        @Override
        public void onSubmit() {
            mProgressDialog.show(); // show loader
        }

        @Override
        public void onSuccess(@NonNull TokenDetails tokenDetails) {
            mProgressDialog.dismiss(); // dismiss the loader
            displayMakePaymentMessage(tokenDetails.getToken());
        }

        @Override
        public void onFailure(@NonNull String errorMessage) {
            mProgressDialog.dismiss(); // dismiss the loader
            displayMessage("Token Error", errorMessage, false);
        }

        @Override
        public void onBackPressed() {
            finish();
        }
    };

    private final Function1<ThreeDSResult, Unit> threeDSResultHandler = threeDSResult -> {
        if (threeDSResult instanceof ThreeDSResult.Success) {
            String token = ((ThreeDSResult.Success) threeDSResult).getToken();
            displayMessage("Result", "Authentication success: " + token, true);
        } else if (threeDSResult instanceof ThreeDSResult.Error) {
            String errorMessage = ((ThreeDSResult.Error) threeDSResult).getError().getMessage();
            displayMessage("Result", "Authentication error: " + errorMessage, true);
        } else {
            displayMessage("Result", "Authentication Failure", true);
        }
        return null;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialise the loader
        mProgressDialog = new ProgressDialog(DemoActivity.this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Loading...");

        mPaymentFormMediator = new PaymentFormMediator(providePaymentFormConfig());
        mPaymentFormMediator.setActivityContent(this);
    }

    @Override
    public void onBackPressed() {
        ViewGroup container = this.findViewById(android.R.id.content);
        View lastChild = container.getChildAt(container.getChildCount() - 1);

        if (lastChild instanceof WebView) container.removeView(lastChild);
        else super.onBackPressed();
    }

    private PaymentFormConfig providePaymentFormConfig() {
        return new PaymentFormConfig(
                Constants.PUBLIC_KEY,
                this,
                Constants.ENVIRONMENT,
                new PaymentFormStyle(),
                mPaymentFlowHandler);
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
        ThreeDSRequest threeDSRequest = new ThreeDSRequest(
                this.findViewById(android.R.id.content),
                redirectUrl,
                Constants.SUCCESS_URL,
                Constants.FAILURE_URL,
                threeDSResultHandler
        );

        mPaymentFormMediator.handleThreeDS(threeDSRequest);
    }
}