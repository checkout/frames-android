package com.checkout.android_sdk;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.checkout.android_sdk.Models.BillingModel;
import com.checkout.android_sdk.Models.PhoneModel;
import com.checkout.android_sdk.Request.CardTokenisationRequest;
import com.checkout.android_sdk.Store.DataStore;
import com.checkout.android_sdk.Utils.CardUtils;
import com.checkout.android_sdk.Utils.CustomAdapter;
import com.checkout.android_sdk.Utils.PhoneUtils;
import com.checkout.android_sdk.View.BillingDetailsView;
import com.checkout.android_sdk.View.CardDetailsView;

import java.util.Locale;

/**
 * Contains helper methods dealing with the tokenisation or payment from customisation
 * <p>
 * Most of the methods that include interaction with the Checkout.com API rely on
 * callbacks to communicate outcomes. Please make sure you set the key/environment
 * and appropriate  callbacks to a ensure successful interaction
 */
public class PaymentForm extends FrameLayout {

    /**
     * This is interface used as a callback for when the 3D secure functionality is used
     */
    public interface On3DSFinished {
        void onSuccess(String token);

        void onError(String errorMessage);
    }

    /**
     * This is interface used as a callback for when the form is completed and the user pressed the
     * pay button. You can use this to potentially display a loader.
     */
    public interface OnSubmitForm {
        void onSubmit(CardTokenisationRequest request);
        void onBackPressed();
    }

    // Indexes for the pages
    private static int CARD_DETAILS_PAGE_INDEX = 0;
    private static int BILLING_DETAILS_PAGE_INDEX = 1;

    /**
     * This is a callback used to generate a payload with the user details and pass them to the
     * mSubmitFormListener so the user can act upon them. The next step will most likely include using
     * this payload to generate a token in  the CheckoutAPIClient
     */
    private final CardDetailsView.DetailsCompleted mDetailsCompletedListener = new CardDetailsView.DetailsCompleted() {
        @Override
        public void onDetailsCompleted() {
            mSubmitFormListener.onSubmit(generateRequest());
            mDataStore.cleanState();
            customAdapter.clearFields();
        }

        @Override
        public void onBackPressed() {
            mSubmitFormListener.onBackPressed();
        }
    };

    /**
     * This is a callback used to go back to the card details view from the billing page
     * and based on the action used decide is the billing spinner will be updated
     */
    private BillingDetailsView.Listener mBillingListener = new BillingDetailsView.Listener() {
        @Override
        public void onBillingCompleted() {
            customAdapter.updateBillingSpinner();
            mPager.setCurrentItem(CARD_DETAILS_PAGE_INDEX);
        }

        @Override
        public void onBillingCanceled() {
            customAdapter.clearBillingSpinner();
            mPager.setCurrentItem(CARD_DETAILS_PAGE_INDEX);
        }
    };

    /**
     * This is a callback used to navigate to the billing details page
     */
    private CardDetailsView.GoToBillingListener mCardListener = new CardDetailsView.GoToBillingListener() {
        @Override
        public void onGoToBillingPressed() {
            mPager.setCurrentItem(BILLING_DETAILS_PAGE_INDEX);
        }
    };


    private Context mContext;
    public On3DSFinished m3DSecureListener;
    public OnSubmitForm mSubmitFormListener;
    public CheckoutAPIClient.OnTokenGenerated mTokenListener;

    private CustomAdapter customAdapter;
    private ViewPager mPager;
    private AttributeSet attrs;
    private DataStore mDataStore = DataStore.getInstance();

    /**
     * This is the constructor used when the module is used without the UI.
     */
    public PaymentForm(@NonNull Context context) {
        this(context, null);
    }

    public PaymentForm(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.attrs = attrs;
        initView();
    }

    /**
     * This method is used to initialise the UI of the module
     */
    private void initView() {
        // Set up the layout
        inflate(mContext, R.layout.payment_form, this);

        mPager = findViewById(R.id.view_pager);
        // Use a custom adapter for the viewpager
        customAdapter = new CustomAdapter(mContext);
        // Set up the callbacks
        customAdapter.setCardDetailsListener(mCardListener);
        customAdapter.setBillingListener(mBillingListener);
        customAdapter.setTokenDetailsCompletedListener(mDetailsCompletedListener);
        mPager.setAdapter(customAdapter);
        mPager.setEnabled(false);
    }

    /**
     * This method is used set the accepted card schemes
     *
     * @param cards array of accepted cards
     */
    public PaymentForm setAcceptedCard(CardUtils.Cards[] cards) {
        mDataStore.setAcceptedCards(cards);
        return this;
    }

    /**
     * This method is used to handle 3D Secure URLs.
     * <p>
     * It wil programmatically generate a WebView and listen for when the url changes
     * in either the success url or the fail url.
     *
     * @param url        the 3D Secure url
     * @param successUrl the Redirection url set up in the Checkout.com HUB
     * @param failsUrl   the Redirection Fail url set up in the Checkout.com HUB
     */
    public void handle3DS(String url, final String successUrl, final String failsUrl) {
        if (mPager != null) {
            mPager.setVisibility(GONE); // dismiss the card form UI
        }
        WebView web = new WebView(mContext);
        web.loadUrl(url);
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient() {
            // Listen for when teh URL changes and match t with either the success of fail url
            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.contains(successUrl)) {
                    Uri uri = Uri.parse(url);
                    String paymentToken = uri.getQueryParameter("cko-payment-token");
                    m3DSecureListener.onSuccess(paymentToken);
                } else if (url.contains(failsUrl)) {
                    Uri uri = Uri.parse(url);
                    String paymentToken = uri.getQueryParameter("cko-payment-token");
                    m3DSecureListener.onError(paymentToken);
                }
            }
        });
        // Make WebView fill the layout
        web.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        addView(web);
    }

    /**
     * This method used to generate a {@link CardTokenisationRequest} with the details
     * completed by the user in the payment from
     * displayed in the payment form.
     *
     * @return CardTokenisationRequest
     */
    private CardTokenisationRequest generateRequest() {
        CardTokenisationRequest request;
        if (mDataStore.isBillingCompleted()) {
            request = new CardTokenisationRequest(
                    sanitizeEntry(mDataStore.getCardNumber()),
                    mDataStore.getCustomerName(),
                    mDataStore.getCardMonth(),
                    mDataStore.getCardYear(),
                    mDataStore.getCardCvv(),
                    new BillingModel(
                            mDataStore.getCustomerAddress1(),
                            mDataStore.getCustomerAddress2(),
                            mDataStore.getCustomerZipcode(),
                            mDataStore.getCustomerCountry(),
                            mDataStore.getCustomerCity(),
                            mDataStore.getCustomerState(),
                            new PhoneModel(
                                    mDataStore.getCustomerPhonePrefix(),
                                    mDataStore.getCustomerPhone()
                            )
                    )
            );
        } else {
            request = new CardTokenisationRequest(
                    sanitizeEntry(mDataStore.getCardNumber()),
                    mDataStore.getCustomerName(),
                    mDataStore.getCardMonth(),
                    mDataStore.getCardYear(),
                    mDataStore.getCardCvv(),
                    null
            );
        }

        return request;
    }

    /**
     * This method used to decide if the billing details option will be
     * displayed in the payment form.
     *
     * @param include boolean showing if the billing should be used
     */
    public void includeBilling(Boolean include) {
        if (!include) {
            mDataStore.setShowBilling(false);
        } else {
            mDataStore.setShowBilling(true);
        }
    }

    /**
     * This method used to set a default country for the country
     *
     * @param country Locale representing the default country for the Spinner
     */
    public PaymentForm setDefaultBillingCountry(Locale country) {
        mDataStore.setCustomerCountry(country.getCountry());
        mDataStore.setDefaultCountry(country);
        mDataStore.setCustomerPhonePrefix(PhoneUtils.getPrefix(country.getCountry()));
        return this;
    }

    /**
     * This method used to set a custom label for the accepted cards
     *
     * @param accepted String representing the value for the Label
     */
    public PaymentForm setAcceptedCardsLabel(String accepted) {
        mDataStore.setAcceptedLabel(accepted);
        return this;
    }

    /**
     * This method used to set a custom label for the CardInput
     *
     * @param card String representing the value for the Label
     */
    public PaymentForm setCardLabel(String card) {
        mDataStore.setCardLabel(card);
        return this;
    }

    /**
     * This method used to set a custom label for the DateInput
     *
     * @param date String representing the value for the Label
     */
    public PaymentForm setDateLabel(String date) {
        mDataStore.setDateLabel(date);
        return this;
    }

    /**
     * This method used to set a custom label for the CvvInput
     *
     * @param cvv String representing the value for the Label
     */
    public PaymentForm setCvvLabel(String cvv) {
        mDataStore.setCvvLabel(cvv);
        return this;
    }

    /**
     * This method used to set a custom label for the CardholderInput
     *
     * @param label String representing the value for the Label
     */
    public PaymentForm setCardHolderLabel(String label) {
        mDataStore.setCardHolderLabel(label);
        return this;
    }

    /**
     * This method used to set a custom label for the AddressLine 1 Input
     *
     * @param label String representing the value for the Label
     */
    public PaymentForm setAddress1Label(String label) {
        mDataStore.setAddressLine1Label(label);
        return this;
    }

    /**
     * This method used to set a custom label for the AddressLine 2 Input
     *
     * @param label String representing the value for the Label
     */
    public PaymentForm setAddress2Label(String label) {
        mDataStore.setAddressLine2Label(label);
        return this;
    }

    /**
     * This method used to set a custom label for the StateInput
     *
     * @param label String representing the value for the Label
     */
    public PaymentForm setTownLabel(String label) {
        mDataStore.setTownLabel(label);
        return this;
    }

    /**
     * This method used to set a custom label for the StateInput
     *
     * @param label String representing the value for the Label
     */
    public PaymentForm setStateLabel(String label) {
        mDataStore.setStateLabel(label);
        return this;
    }

    /**
     * This method used to set a custom label for the PostcodeInput
     *
     * @param label String representing the value for the Label
     */
    public PaymentForm setPostcodeLabel(String label) {
        mDataStore.setPostCodeLabel(label);
        return this;
    }

    /**
     * This method used to set a custom label for the PhoneInput
     *
     * @param label String representing the value for the Label
     */
    public PaymentForm setPhoneLabel(String label) {
        mDataStore.setPhoneLabel(label);
        return this;
    }

    /**
     * This method used to inject address details if they have already been collected
     *
     * @param billing BillingModel representing the value for the billing details
     */
    public PaymentForm injectBilling(BillingModel billing) {
        mDataStore.setBillingCompleted(true);
        mDataStore.setCustomerAddress1(billing.getAddressLine1());
        mDataStore.setCustomerAddress2(billing.getAddressLine2());
        mDataStore.setCustomerZipcode(billing.getPostcode());
        mDataStore.setCustomerCountry(billing.getCountry());
        mDataStore.setCustomerCity(billing.getCity());
        mDataStore.setCustomerState(billing.getState());
        mDataStore.setCustomerPhone(billing.getPhone().getNumber());
        mDataStore.setCustomerPhonePrefix(billing.getPhone().getCountryCode());
        return this;
    }

    /**
     * This method used to inject the cardholder name if it has already been collected
     *
     * @param name String representing the value for the cardholder name
     */
    public PaymentForm injectCardHolderName(String name) {
        mDataStore.setCustomerName(name);
        return this;
    }


    /**
     * Returns a String without any spaces
     * <p>
     * This method used to take a card number input String and return a
     * String that simply removed all whitespace, keeping only digits.
     *
     * @param entry the String value of a card number
     */
    private String sanitizeEntry(String entry) {
        return entry.replaceAll("\\D", "");
    }

    /**
     * This method used to set a callback for when the 3D Secure handling.
     */
    public PaymentForm set3DSListener(On3DSFinished listener) {
        this.m3DSecureListener = listener;
        return this;
    }

    /**
     * This method used to set a callback for when the form is submitted
     */
    public PaymentForm setSubmitListener(OnSubmitForm listener) {
        this.mSubmitFormListener = listener;
        return this;
    }

}
