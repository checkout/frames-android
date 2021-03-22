package checkout.com.demo.Demos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.checkout.android_sdk.CheckoutAPIClient;
import com.checkout.android_sdk.CheckoutAPIClient.OnTokenGenerated;
import com.checkout.android_sdk.Request.CardTokenisationRequest;
import com.checkout.android_sdk.Response.CardTokenisationFail;
import com.checkout.android_sdk.Response.CardTokenisationResponse;
import com.checkout.android_sdk.Utils.CardUtils;
import com.checkout.android_sdk.Utils.Environment;
import com.checkout.android_sdk.network.NetworkError;

import checkout.com.demo.R;

public class CustomFieldsDemo extends Activity {

    CheckoutAPIClient mCheckoutAPIClient;

    private final CheckoutAPIClient.OnTokenGenerated mTokenListener = new OnTokenGenerated() {

        @Override
        public void onTokenGenerated(CardTokenisationResponse token) {
            mProgressDialog.dismiss();
            displayMessage("Success!", token.getToken());
        }

        @Override
        public void onError(CardTokenisationFail error) {
            mProgressDialog.dismiss();
            displayMessage("Error!", error.getErrorType());
        }

        @Override
        public void onNetworkError(NetworkError error) {
            mProgressDialog.dismiss();
            displayMessage("Network Error", String.valueOf(error));
        }
    };

    private EditText mName, mCard, mMonth, mYear, mCvv;
    private Button mPay;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_fields_activity);

        // initialise the loader
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Loading...");

        mName = findViewById(R.id.name_input);
        mCard = findViewById(R.id.card_input);
        mMonth = findViewById(R.id.month_input);
        mYear = findViewById(R.id.year_input);
        mCvv = findViewById(R.id.cvv_input);
        mPay = findViewById(R.id.pay_button);

        mCheckoutAPIClient = new CheckoutAPIClient(
                this,
                "pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73",
                Environment.SANDBOX
        );
        mCheckoutAPIClient.setTokenListener(mTokenListener); // pass the callback

        mPay.setOnClickListener(v -> {
            if (formValidationOutcome()) {
                mProgressDialog.show(); // show loader
                try {
                    mCheckoutAPIClient.generateToken(
                            new CardTokenisationRequest(
                                    mCard.getText().toString(),
                                    mName.getText().toString(),
                                    mMonth.getText().toString(),
                                    mYear.getText().toString(),
                                    mCvv.getText().toString()
                            )
                    );
                } catch (Exception e) {
                    mProgressDialog.hide();
                    displayMessage("Exception", Log.getStackTraceString(e));
                }
            }
        });
    }

    private boolean formValidationOutcome() {

        boolean outcome = true;

        if (!CardUtils.isValidCard(mCard.getText().toString())) {
            mCard.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            outcome = false;
        } else {
            mCard.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
        }
        if (!CardUtils.isValidDate(mMonth.getText().toString(), mYear.getText().toString())) {
            mMonth.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            mYear.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            outcome = false;
        } else {
            mMonth.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
            mYear.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
        }

        if (!CardUtils.isValidCvv(mCvv.getText().toString(), CardUtils.getType(mCard.getText().toString()))) {
            mCvv.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            outcome = false;
        } else {
            mCvv.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
        }

        return outcome;
    }

    private void displayMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> { /*do things */ });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
