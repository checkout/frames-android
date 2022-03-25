package com.checkout.android_sdk.View;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.checkout.android_sdk.CheckoutAPIClient;
import com.checkout.android_sdk.Input.BillingInput;
import com.checkout.android_sdk.Input.CardInput;
import com.checkout.android_sdk.Input.CvvInput;
import com.checkout.android_sdk.Input.DefaultInput;
import com.checkout.android_sdk.Input.MonthInput;
import com.checkout.android_sdk.Input.YearInput;
import com.checkout.android_sdk.Models.BillingModel;
import com.checkout.android_sdk.Models.PhoneModel;
import com.checkout.android_sdk.R;
import com.checkout.android_sdk.Request.CardTokenisationRequest;
import com.checkout.android_sdk.Response.CardTokenisationFail;
import com.checkout.android_sdk.Response.CardTokenisationResponse;
import com.checkout.android_sdk.Store.DataStore;
import com.checkout.android_sdk.Utils.BackNavigationHandler;
import com.checkout.android_sdk.Utils.BackNavigationHandler.BackNavigationListener;
import com.checkout.android_sdk.Utils.CardUtils;
import com.checkout.android_sdk.Utils.KeyboardUtils;
import com.checkout.android_sdk.View.data.LoggingState;
import com.checkout.android_sdk.network.NetworkError;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * The controller of the card details view page
 * <p>
 * This class handles interaction with the custom inputs in the card details form.
 * The state of the view is handled here, so are action like focus changes, full form
 * validation, listeners, persistence over orientation.
 */
public class CardDetailsView extends LinearLayout {

    /**
     * The callback used to indicate the form submission
     * <p>
     * After the user completes their details and the form is valid this callback will
     * be used to communicate to the parent and start the necessary API call(s).
     */
    public interface DetailsCompleted {
        void onFormSubmit();
        void onTokeGenerated(CardTokenisationResponse reponse);
        void onError(CardTokenisationFail error);
        void onNetworkError(NetworkError error);
        void onBackPressed();
    }

    /**
     * The callback used to indicate the view needs to moved to the billing details page
     * <p>
     * When the user selects the option to add billing details this callback will be used
     * to communicate to the parent the focus change is requested
     */
    public interface GoToBillingListener {
        void onGoToBillingPressed();
    }

    /**
     * The callback is used to communicate with the card input
     * <p>
     * The custom {@link CardInput} takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private final CardInput.Listener mCardInputListener = new CardInput.Listener() {
        @Override
        public void onCardInputFinish(@NonNull String number) {
            mDataStore.setValidCardNumber(true);
        }

        @Override
        public void onCardError() {
            mCardLayout.setError(getResources().getString(R.string.error_card_number));
            mDataStore.setValidCardNumber(false);
        }

        @Override
        public void onClearCardError() {
            mCardLayout.setError(null);
            mCardLayout.setErrorEnabled(false);
        }
    };

    /**
     * The callback is used to communicate with the month input
     * <p>
     * The custom {@link MonthInput} takes care of populating the values in the spinner
     * and will trigger this callback when the user selects a new option. State is update
     * accordingly.
     */
    private final MonthInput.MonthListener mMonthInputListener = new MonthInput.MonthListener() {
        @Override
        public void onMonthInputFinish(@NonNull String month) {
            mDataStore.setCardMonth(month);
            mDataStore.setValidCardMonth(true);
        }
    };

    /**
     * The callback is used to communicate with the year input
     * <p>
     * The custom {@link YearInput} takes care of populating the values in the spinner
     * and will trigger this callback when the user selects a new option. State is update
     * accordingly.
     */
    private final YearInput.YearListener mYearInputListener = new YearInput.YearListener() {
        @Override
        public void onYearInputFinish(@NonNull String year) {
            mDataStore.setCardYear(year);
            mDataStore.setValidCardYear(true);
            ((TextView) mYearInput.getSelectedView()).setError(null);
        }
    };

    /**
     * The callback is used to communicate with the cvv input
     * <p>
     * The custom {@link DefaultInput} takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private final DefaultInput.Listener mCvvInputListener = new DefaultInput.Listener() {
        @Override
        public void onInputFinish(String value) {
            mDataStore.setCardCvv(value);
            mDataStore.setValidCardCvv(value.length() == mDataStore.getCvvLength());
        }

        @Override
        public void clearInputError() {
            mCvvLayout.setError(null);
            mCvvLayout.setErrorEnabled(false);
        }
    };

    // Callback used for the outcome of the generating a token
    private final CheckoutAPIClient.OnTokenGenerated mTokenListener = new CheckoutAPIClient.OnTokenGenerated() {
        @Override
        public void onTokenGenerated(CardTokenisationResponse token) {
            mDetailsCompletedListener.onTokeGenerated(token);
        }

        @Override
        public void onError(CardTokenisationFail error) {
            mDetailsCompletedListener.onError(error);
        }

        @Override
        public void onNetworkError(NetworkError error) {
            mDetailsCompletedListener.onNetworkError(error);
        }
    };

    /**
     * The callback is used to trigger the focus change to the billing page
     */
    private final BillingInput.BillingListener mBillingInputListener = new BillingInput.BillingListener() {
        @Override
        public void onGoToBilling() {
            if (mGotoBillingListener != null) {
                mGotoBillingListener.onGoToBillingPressed();
            }
        }
    };

    private final BackNavigationListener mBackNavigationListener = (event, view) -> onBackPressed();

    @NonNull
    private final DataStore mDataStore = DataStore.getInstance();
    @NonNull
    private final BackNavigationHandler mBackNavigationHandler = new BackNavigationHandler(mBackNavigationListener);
    @Nullable
    private CardDetailsView.GoToBillingListener mGotoBillingListener;
    @Nullable
    private CardDetailsView.DetailsCompleted mDetailsCompletedListener;
    @NonNull
    private LoggingState mLoggingState;

    private CardInput mCardInput;
    private MonthInput mMonthInput;
    private YearInput mYearInput;
    private BillingInput mGoToBilling;
    private CvvInput mCvvInput;
    private TextInputLayout mCardLayout;
    private TextInputLayout mCvvLayout;
    private LinearLayout mAcceptedCardsView;

    public CardDetailsView(Context context) {
        this(context, null, new LoggingState());
    }

    public CardDetailsView(Context context, @Nullable AttributeSet attrs) {
        this(context, null, new LoggingState());
    }

    public CardDetailsView(Context context, LoggingState loggingStat) {
        this(context, null, loggingStat);
    }

    private CardDetailsView(Context context, @Nullable AttributeSet attrs, @NonNull LoggingState loggingState) {
        super(context, attrs);
        this.mLoggingState = loggingState;
        initialise(context);
    }

    /**
     * The UI initialisation
     * <p>
     * Used to initialise element and pass callbacks as well as setting up appropriate listeners
     */
    private void initialise(Context context) {
        inflate(context, R.layout.card_details, this);

        Toolbar toolbar = findViewById(R.id.my_toolbar);

        mCardInput = findViewById(R.id.card_input);
        mCardLayout = findViewById(R.id.card_input_layout);
        mCardInput.setCardListener(mCardInputListener);

        mMonthInput = findViewById(R.id.month_input);
        mMonthInput.setMonthListener(mMonthInputListener);

        mYearInput = findViewById(R.id.year_input);
        mYearInput.setYearListener(mYearInputListener);

        mCvvInput = findViewById(R.id.cvv_input);
        mCvvLayout = findViewById(R.id.cvv_input_layout);
        mCvvInput.setListener(mCvvInputListener);

        TextView billingHelper = findViewById(R.id.billing_helper_text);
        mGoToBilling = findViewById(R.id.go_to_billing);

        TextView acceptedCardsHelper = findViewById(R.id.accepted_card_helper);
        TextView dateHelper = findViewById(R.id.date_helper);

        toolbar.setNavigationOnClickListener(view -> {
            if (mDetailsCompletedListener != null) {
                mDetailsCompletedListener.onBackPressed();
            }
        });

        // Hide billing details options based on the module initialisation option
        if (!mDataStore.getBillingVisibility()) {
            billingHelper.setVisibility(GONE);
            mGoToBilling.setVisibility(GONE);
        } else {
            mGoToBilling.setBillingListener(mBillingInputListener);
        }

        Button payButton = findViewById(R.id.pay_button);
        if(mDataStore.getPayButtonText() != null) {
            payButton.setText(mDataStore.getPayButtonText());
        }
        if(mDataStore.getPayButtonLayout() != null) {
            payButton.setLayoutParams(mDataStore.getPayButtonLayout());
        }

        payButton.setOnClickListener(v -> {
            // hide keyboard
            KeyboardUtils.hideSoftKeyboard(this);
            if (mDetailsCompletedListener != null && isValidForm()) {
                mDetailsCompletedListener.onFormSubmit();
                CheckoutAPIClient checkoutAPIClient = new CheckoutAPIClient(
                        getContext(), // context
                        mDataStore.getKey(), // your public key
                        mDataStore.getEnvironment()
                );
                checkoutAPIClient.setTokenListener(mTokenListener);
                checkoutAPIClient.setCorrelationID(mLoggingState.getCorrelationId());
                CardTokenisationRequest request = generateRequest();
                try {
                    checkoutAPIClient.generateToken(request);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Restore state in case the orientation changes
        repopulateField();

        // Populate accepted cards
        mAcceptedCardsView = findViewById(R.id.card_icons_layout);
        setAcceptedCards();

        // Set custom labels
        if(mDataStore.getAcceptedLabel() != null) {
            acceptedCardsHelper.setText(mDataStore.getAcceptedLabel());
        }
        if(mDataStore.getCardLabel() != null) {
            mCardLayout.setHint(mDataStore.getCardLabel());
        }
        if(mDataStore.getDateLabel() != null) {
            dateHelper.setText(mDataStore.getDateLabel());
        }
        if(mDataStore.getCvvLabel() != null) {
            mCvvLayout.setHint(mDataStore.getCvvLabel());
        }
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle savedBundle = new Bundle();
        savedBundle.putParcelable("CardDetailsView.rootState", super.onSaveInstanceState());
        savedBundle.putParcelable("mLoggingState", mLoggingState);
        return savedBundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Parcelable rootViewState = state;
        if (state instanceof Bundle) {
            Bundle savedBundle = (Bundle) state;
            rootViewState = savedBundle.getParcelable("CardDetailsView.rootState");
            mLoggingState = savedBundle.getParcelable("mLoggingState");
        }
        super.onRestoreInstanceState(rootViewState);
    }

    /**
     * Used to restore state on orientation changes
     * <p>
     * The method will repopulate all the card input fields with the latest state they were in
     * if the device orientation changes, and therefore avoiding the text inputs to be cleared.
     */
    private void repopulateField() {
        // Repopulate card cvv
        if (mDataStore.getCardCvv() != null) {
            // Update the cvv field with the last input value
            mCvvInput.setText(mDataStore.getCardCvv());
        }

        //Repopulate billing spinner
        updateBillingSpinner();
    }

    /**
     * Used to indicate the validity of the full card from
     * <p>
     * The method will check if the inputs are valid and also check the relation between the inputs
     * to ensure validity (e.g. month to year relation).
     * This method will also populate the field error accordingly
     *
     * @return boolean abut form validity
     */
    private boolean isValidForm() {

        boolean outcome = true;

        checkFullDate();
        if (!mDataStore.isValidCardMonth()) {
            outcome = false;
        }

        if (!mDataStore.isValidCardNumber()) {
            mCardLayout.setError(getResources().getString(R.string.error_card_number));
            outcome = false;
        }

        mDataStore.setValidCardCvv(mCvvInput.getText() != null && mCvvInput.getText().length() == mDataStore.getCvvLength());
        if (!mDataStore.isValidCardCvv()) {
            mCvvLayout.setError(getResources().getString(R.string.error_cvv));
            outcome = false;
        } else {
            mCvvLayout.setError(null);
            mCvvLayout.setErrorEnabled(false);
        }

        return outcome;
    }

    /**
     * Used to indicate the validity of the date
     * <p>
     * The method will check if the values from the {@link MonthInput} and {@link YearInput} are
     * not representing a date in the past.
     */
    private void checkFullDate() {

        // Check is the state contain the date and if it is check if the current selected
        // values are valid. Display error if applicable.
        if (mDataStore.getCardYear() != null &&
                mDataStore.getCardYear() != null &&
                !CardUtils.isValidDate(mDataStore.getCardMonth(), mDataStore.getCardYear())) {
            mDataStore.setValidCardMonth(false);
           if(mMonthInput.getSelectedView()!=null){
               ((TextView) mMonthInput.getSelectedView()).setError(getResources()
                       .getString(R.string.error_expiration_date));
           }
        }
        mDataStore.setValidCardMonth(true);
    }

    /**
     * Used to clear/reset the billing details spinner
     * <p>
     * The method will be used to clear/reset the billing details spinner in case the user
     * has decide to clear their details from the {@link BillingDetailsView}
     */
    public void clearBillingSpinner() {
        List<String> billingElement = new ArrayList<>();

        // Set the default value fo the spinner
        billingElement.add(getResources().getString(R.string.select_billing_details));
        billingElement.add(getResources().getString(R.string.billing_details_add));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, billingElement);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGoToBilling.setAdapter(dataAdapter);
        mGoToBilling.setSelection(0);
    }

    /**
     * Used to populate the billing spinner with the user billing details
     * <p>
     * The method will be called when the user has successfully saved their billing details and
     * to visually confirm that, the spinner is populated with the details and the default ADD
     * button is replaced by a EDIT button
     */
    public void updateBillingSpinner() {

        StringBuilder address = new StringBuilder();
        boolean hasAddress = false;

        String[] addressComponents = {
                mDataStore.getCustomerAddress1(),
                mDataStore.getCustomerAddress2(),
                mDataStore.getCustomerCity(),
                mDataStore.getCustomerState()
        };
        for (String component : addressComponents) {
            if (component.isEmpty()) continue;
            if (address.length() > 0) address.append(", ");

            address.append(component);
            hasAddress = true;
        }

        // Avoid updates for there are no values set
        if (hasAddress) {
            List<String> billingElement = new ArrayList<>();

            billingElement.add(address.toString().trim());
            billingElement.add(getResources().getString(R.string.billing_details_edit));

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_item, billingElement);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mGoToBilling.setAdapter(dataAdapter);
        }

        mGoToBilling.setSelection(0);
    }

    /**
     * Used to clear the text and state of the fields
     * <p>
     */
    public void resetFields() {
        if(mDataStore.getDefaultBillingDetails() != null) {
            updateBillingSpinner();
        } else {
            clearBillingSpinner();
        }
        mCvvInput.setText("");
        mCvvLayout.setError(null);
        mCvvLayout.setErrorEnabled(false);
        mYearInput.setSelection(0);
        mMonthInput.setSelection(0);
        mCardInput.clear();
        mCardLayout.setError(null);
        mCardLayout.setErrorEnabled(false);
    }

    /**
     * Used dynamically populate the accepted cards view is the option is used
     */
    public void setAcceptedCards() {

        CardUtils.Cards[] acceptedCards = mDataStore.getAcceptedCards();
        if (acceptedCards == null) {
            acceptedCards = CardUtils.Cards.values();
        }

        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getResources().getDisplayMetrics());
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());

        for (CardUtils.Cards card : acceptedCards) {
            ImageView image = new ImageView(getContext());
            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(size, size));
            image.setImageResource(card.resourceId);
            MarginLayoutParams marginParams = new MarginLayoutParams(image.getLayoutParams());
            marginParams.setMargins(0, 0, margin, 0);

            // Adds the view to the layout
            mAcceptedCardsView.addView(image);
        }

    }

    /**
     * This method used to generate a {@link CardTokenisationRequest} with the details
     * completed by the user in the payment from
     * displayed in the payment form.
     *
     * @return CardTokenisationRequest
     */
    private CardTokenisationRequest generateRequest() {
        BillingModel billingModel = null;
        PhoneModel phoneModel = null;
        if (mDataStore.isBillingCompleted()) {
            billingModel = new BillingModel(
                    mDataStore.getCustomerAddress1(),
                    mDataStore.getCustomerAddress2(),
                    mDataStore.getCustomerZipcode(),
                    mDataStore.getCustomerCountry(),
                    mDataStore.getCustomerCity(),
                    mDataStore.getCustomerState()
            );
            phoneModel = new PhoneModel(
                    mDataStore.getCustomerPhonePrefix(),
                    mDataStore.getCustomerPhone()
            );
        }


        return new CardTokenisationRequest(
                sanitizeNumericEntry(mDataStore.getCardNumber()),
                mDataStore.getCustomerName(),
                mDataStore.getCardMonth(),
                mDataStore.getCardYear(),
                mDataStore.getCardCvv(),
                billingModel,
                phoneModel
        );
    }

    /**
     * Returns a String that contains only digits (without any spaces).
     * <p>
     * This method used to take a card number input String and return a
     * String that simply removed all whitespace, keeping only digits.
     *
     * @param entry the String value of a card number
     */
    private String sanitizeNumericEntry(String entry) {
        return entry.replaceAll("\\D", "");
    }


    /**
     * Used to set the callback listener for when the form is submitted
     */
    public void setDetailsCompletedListener(CardDetailsView.DetailsCompleted listener) {
        mDetailsCompletedListener = listener;
    }

    /**
     * Used to set the callback listener for when the billing details page is requested
     */
    public void setGoToBillingListener(GoToBillingListener listener) {
        mGotoBillingListener = listener;
    }

    // Capture back key events so that onBackPressed can be invoked.
    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        boolean consumed = super.dispatchKeyEventPreIme(event);

        if (!consumed && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            consumed = mBackNavigationHandler.processKeyEvent(event, this);
        }

        return consumed;
    }

    private void onBackPressed() {
        View focusedView = getFocusedChild();
        if (focusedView == null || !KeyboardUtils.hideSoftKeyboard(focusedView)) {
            if (mDetailsCompletedListener != null) {
                mDetailsCompletedListener.onBackPressed();
            }
        }
    }

    public void setLoggingState(@NonNull LoggingState loggingState) {
        mLoggingState = loggingState;
    }
}
