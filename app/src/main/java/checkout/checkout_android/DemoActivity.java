package checkout.checkout_android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.android.volley.VolleyError;
import com.checkout.sdk.CheckoutClient;
import com.checkout.sdk.paymentform.PaymentForm;
import com.checkout.sdk.response.CardTokenisationFail;
import com.checkout.sdk.response.CardTokenisationResponse;
import com.checkout.sdk.utils.CardUtils.Cards;
import com.checkout.sdk.utils.Environment;

public class DemoActivity extends Activity {

    private PaymentForm mPaymentForm;
    private ProgressDialog mProgressDialog;


    private final CheckoutClient.OnTokenGenerated tokenListener = new CheckoutClient.OnTokenGenerated() {

        @Override
        public void onTokenGenerated(CardTokenisationResponse response) {
            mProgressDialog.dismiss(); // dismiss the loader
            mPaymentForm.clearForm(); // clear the form
            displayMessage("Token", response.getId());
        }

        @Override
        public void onError(CardTokenisationFail response) {
            mProgressDialog.dismiss();
            displayMessage("Token Error", response.getErrorCode());
        }

        @Override
        public void onNetworkError(VolleyError error) {
            mProgressDialog.dismiss();
            displayMessage("Network Error", String.valueOf(error));
        }

        @Override
        public void onTokenRequested() {
            mProgressDialog.show();
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
        CheckoutClient checkoutClient = new CheckoutClient(
                this,
                "pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73",
                Environment.SANDBOX,
                tokenListener);
        mPaymentForm
                .setAcceptedCard(new Cards[]{Cards.VISA, Cards.MASTERCARD})
                .initialize(checkoutClient);

        // TODO: Make it look more like:
        // CheckoutClient checkoutClient = new CheckoutClient(this,
//        "pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73",
//                tokenListener,
//                Environment.SANDBOX
//        );
//        UiCustomizer uiCustomizer = new UiCustomizer.Builder()
//                .acceptedCards(Arrays.asList(Cards.VISA, Cards.MASTERCARD))
//                .build();
//        mPaymentForm = findViewById(R.id.checkout_card_form);
//        mPaymentForm.initialize(checkoutClient, mFormListener, uiCustomizer);
    }

    private void displayMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
