package com.checkout.android_sdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.checkout.android_sdk.Models.BillingModel;
import com.checkout.android_sdk.Models.PhoneModel;
import com.checkout.android_sdk.Response.CardTokenisationFail;
import com.checkout.android_sdk.Response.CardTokenisationResponse;
import com.checkout.android_sdk.Store.DataStore;
import com.checkout.android_sdk.Utils.CardUtils;
import com.checkout.android_sdk.Utils.CustomAdapter;
import com.checkout.android_sdk.Utils.Environment;
import com.checkout.android_sdk.Utils.PhoneUtils;
import com.checkout.android_sdk.View.BillingDetailsView;
import com.checkout.android_sdk.View.CardDetailsView;
import com.checkout.android_sdk.network.NetworkError;

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
    public interface PaymentFormCallback {
        void onFormSubmit();
        void onTokenGenerated(CardTokenisationResponse response);
        void onError(CardTokenisationFail response);
        void onNetworkError(NetworkError error);
        void onBackPressed();
    }

    /**
     * This is a callback used to generate a payload with the user details and pass them to the
     * mSubmitFormListener so the user can act upon them. The next step will most likely include using
     * this payload to generate a token in  the CheckoutAPIClient
     */
    private final CardDetailsView.DetailsCompleted mDetailsCompletedListener = new CardDetailsView.DetailsCompleted() {
        @Override
        public void onFormSubmit() {
            mSubmitFormListener.onFormSubmit();
        }

        @Override
        public void onTokeGenerated(CardTokenisationResponse reponse) {
            mSubmitFormListener.onTokenGenerated(reponse);
        }

        @Override
        public void onError(CardTokenisationFail error) {
            mSubmitFormListener.onError(error);
        }

        @Override
        public void onNetworkError(NetworkError error) {
            mSubmitFormListener.onNetworkError(error);
        }

        @Override
        public void onBackPressed() {
            mDataStore.cleanState();
            mDataStore.setLastCustomerNameState(null);
            mDataStore.setLastBillingValidState(null);
            mDataStore.setLastPhoneValidState(null);
            customAdapter.clearFields();
            mSubmitFormListener.onBackPressed();
        }
    };

    /**
     * This is a callback used to go back to the card details view from the billing page
     * and based on the action used decide is the billing spinner will be updated
     */
    private final BillingDetailsView.Listener mBillingListener = new BillingDetailsView.Listener() {
        @Override
        public void onBillingCompleted() {
            customAdapter.updateBillingSpinner();
            mPager.setCurrentItem(CustomAdapter.CARD_DETAILS_PAGE_INDEX);
        }

        @Override
        public void onBillingCanceled() {
            customAdapter.clearBillingSpinner();
            mPager.setCurrentItem(CustomAdapter.CARD_DETAILS_PAGE_INDEX);
        }
    };

    /**
     * This is a callback used to navigate to the billing details page
     */
    private final CardDetailsView.GoToBillingListener mCardListener = new CardDetailsView.GoToBillingListener() {
        @Override
        public void onGoToBillingPressed() {
            mPager.setCurrentItem(CustomAdapter.BILLING_DETAILS_PAGE_INDEX);
        }
    };


    private final Context mContext;
    public On3DSFinished m3DSecureListener;
    public PaymentFormCallback mSubmitFormListener;

    private CustomAdapter customAdapter;
    private ViewPager mPager;
    @NonNull
    private final DataStore mDataStore = DataStore.getInstance();

    private boolean mPaymentFormPresentedEventGenerated = false;

    /**
     * This is the constructor used when the module is used without the UI.
     */
    public PaymentForm(@NonNull Context context) {
        this(context, null);
    }

    public PaymentForm(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
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
        customAdapter = new CustomAdapter();
        // Set up the callbacks
        customAdapter.setCardDetailsListener(mCardListener);
        customAdapter.setBillingListener(mBillingListener);
        customAdapter.setTokenDetailsCompletedListener(mDetailsCompletedListener);
        mPager.setAdapter(customAdapter);
        mPager.setEnabled(false);
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle savedBundle = new Bundle();
        savedBundle.putParcelable("RootViewState", super.onSaveInstanceState());
        savedBundle.putBoolean("mPaymentFormPresentedEventGenerated", mPaymentFormPresentedEventGenerated);
        return savedBundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Parcelable rootViewState = state;
        if (state instanceof Bundle) {
            Bundle savedBundle = (Bundle) state;
            rootViewState = savedBundle.getParcelable("RootViewState");
            mPaymentFormPresentedEventGenerated = savedBundle.getBoolean("mPaymentFormPresentedEventGenerated");
        }
        super.onRestoreInstanceState(rootViewState);
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
    @SuppressLint("SetJavaScriptEnabled") // JavaScript required for 3DS1 challenge flow
    public void handle3DS(String url, final String successUrl, final String failsUrl) {
        if (mPager != null) {
            mPager.setVisibility(GONE); // dismiss the card form UI
        }
        WebView web = new WebView(mContext);
        web.loadUrl(url);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.setWebViewClient(new WebViewClient() {
            // Listen for when the URL changes and match it with either the success of fail url
            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.contains(successUrl)) {
                    m3DSecureListener.onSuccess(getToken(url));
                } else if (url.contains(failsUrl)) {
                    m3DSecureListener.onError(getToken(url));
                }
            }

            private String getToken(String redirectUrl) {
                Uri uri = Uri.parse(redirectUrl);
                String token = uri.getQueryParameter("cko-payment-token");
                if (token == null) {
                    token = uri.getQueryParameter("cko-session-id");
                }
                return token;
            }
        });
        // Make WebView fill the layout
        web.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        addView(web);
    }

    /**
     * This method used to decide if the billing details option will be
     * displayed in the payment form.
     *
     * @param include boolean showing if the billing should be used
     */
    public void includeBilling(Boolean include) {
        mDataStore.setShowBilling(include);
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
     * This method used to set a custom text for the Pay button
     *
     * @param text String representing the text for the Button
     */
    public PaymentForm setPayButtonText(String text) {
        mDataStore.setPayButtonText(text);
        return this;
    }

    /**
     * This method used to set a custom text for the Done button
     *
     * @param text String representing the text for the Button
     */
    public PaymentForm setDoneButtonText(String text) {
        mDataStore.setDoneButtonText(text);
        return this;
    }

    /**
     * This method used to set a custom text for the Clear button
     *
     * @param text String representing the text for the Button
     */
    public PaymentForm setClearButtonText(String text) {
        mDataStore.setClearButtonText(text);
        return this;
    }

    /**
     * This method used to set a custom LayoutParameters for the Pay button
     *
     * @param layout LayoutParameters representing the style for the Button
     */
    public PaymentForm setPayButtonLayout(LinearLayout.LayoutParams layout) {
        mDataStore.setPayButtonLayout(layout);
        return this;
    }

    /**
     * This method used to set a custom LayoutParameters for the Done button
     *
     * @param layout LayoutParameters representing the style for the Button
     */
    public PaymentForm setDoneButtonLayout(LinearLayout.LayoutParams layout) {
        mDataStore.setDoneButtonLayout(layout);
        return this;
    }

    /**
     * This method used to set a custom LayoutParameters for the Clear button
     *
     * @param layout LayoutParameters representing the style for the Button
     */
    public PaymentForm setClearButtonLayout(LinearLayout.LayoutParams layout) {
        mDataStore.setClearButtonLayout(layout);
        return this;
    }

    /**
     * This method used to inject address details if they have already been collected
     *
     * @param billing BillingModel representing the value for the billing details
     */
    public PaymentForm injectBilling(BillingModel billing) {
        mDataStore.setBillingCompleted(true);
        mDataStore.setLastBillingValidState(billing);
        mDataStore.setDefaultBillingDetails(billing);
        mDataStore.setCustomerAddress1(billing.getAddress_line1());
        mDataStore.setCustomerAddress2(billing.getAddress_line2());
        mDataStore.setCustomerZipcode(billing.getZip());
        mDataStore.setCustomerCountry(billing.getCountry());
        mDataStore.setCustomerCity(billing.getCity());
        mDataStore.setCustomerState(billing.getState());
        return this;
    }

    /**
     * This method used to inject phone details if they have already been collected
     *
     * @param phone PhoneModel representing the value for the phone details
     */
    public PaymentForm injectPhone(PhoneModel phone) {
        mDataStore.setLastPhoneValidState(phone);
        mDataStore.setDefaultPhoneDetails(phone);
        mDataStore.setCustomerPhone(phone.getNumber());
        mDataStore.setCustomerPhonePrefix(phone.getCountry_code());
        return this;
    }

    public PaymentForm setEnvironment(Environment env) {
        mDataStore.setEnvironment(env);
        post(() -> {
            if (!mPaymentFormPresentedEventGenerated) {
                FramesLogger.log(() -> {
                    FramesLogger logger = new FramesLogger();
                    logger.initialise(mContext.getApplicationContext(), env);
                    logger.sendPaymentFormPresentedEvent();
                    mPaymentFormPresentedEventGenerated = true;
                });
            }
        });
        return this;
    }

    public PaymentForm setKey(String key) {
        mDataStore.setKey(key);
        return this;
    }

    /**
     * This method used to inject the cardholder name if it has already been collected
     *
     * @param name String representing the value for the cardholder name
     */
    public PaymentForm injectCardHolderName(String name) {
        mDataStore.setCustomerName(name);
        mDataStore.setDefaultCustomerName(name);
        mDataStore.setLastCustomerNameState(name);
        return this;
    }

    /**
     * This method used to clear the state and fields of the Payment Form
     */
    public void clearForm() {
        mDataStore.cleanState();
        if(mDataStore.getDefaultBillingDetails() != null) {
            mDataStore.setBillingCompleted(true);
            mDataStore.setLastBillingValidState(mDataStore.getDefaultBillingDetails());
            mDataStore.setCustomerAddress1(mDataStore.getDefaultBillingDetails().getAddress_line1());
            mDataStore.setCustomerAddress2(mDataStore.getDefaultBillingDetails().getAddress_line2());
            mDataStore.setCustomerZipcode(mDataStore.getDefaultBillingDetails().getZip());
            mDataStore.setCustomerCountry(mDataStore.getDefaultBillingDetails().getCountry());
            mDataStore.setCustomerCity(mDataStore.getDefaultBillingDetails().getCity());
            mDataStore.setCustomerState(mDataStore.getDefaultBillingDetails().getState());
            mDataStore.setCustomerPhone(mDataStore.getDefaultPhoneDetails().getNumber());
            mDataStore.setCustomerPhonePrefix(mDataStore.getDefaultPhoneDetails().getCountry_code());
        }
        if(mDataStore.getDefaultCustomerName() != null) {
            mDataStore.setCustomerName(mDataStore.getDefaultCustomerName());
        } else {
            mDataStore.setLastCustomerNameState(null);
        }
        if(mDataStore.getDefaultCountry() != null) {
            mDataStore.setDefaultCountry(mDataStore.getDefaultCountry());
        }
        customAdapter.clearFields();
        if(mDataStore.getDefaultBillingDetails() != null) {
            mDataStore.setBillingCompleted(true);
            mDataStore.setLastBillingValidState(mDataStore.getDefaultBillingDetails());
        } else {
            mDataStore.setLastBillingValidState(null);
        }
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
    public PaymentForm setFormListener(PaymentFormCallback listener) {
        this.mSubmitFormListener = listener;
        return this;
    }

}
