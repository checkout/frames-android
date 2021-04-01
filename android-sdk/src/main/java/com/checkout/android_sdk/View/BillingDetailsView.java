package com.checkout.android_sdk.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.checkout.android_sdk.Input.AddressOneInput;
import com.checkout.android_sdk.Input.CountryInput;
import com.checkout.android_sdk.Input.DefaultInput;
import com.checkout.android_sdk.Input.PhoneInput;
import com.checkout.android_sdk.Models.BillingModel;
import com.checkout.android_sdk.Models.PhoneModel;
import com.checkout.android_sdk.R;
import com.checkout.android_sdk.Store.DataStore;
import com.checkout.android_sdk.Utils.PhoneUtils;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

/**
 * The controller of the billing details view page
 * <p>
 * This class handles interaction with the custom inputs in the billing details form.
 * The state of the view is handled here, so are action like focus changes, full form
 * validation, listeners, persistence over orientation.
 */
public class BillingDetailsView extends LinearLayout {
    /**
     * The callback used to indicate is the billing details were completed
     * <p>
     * After the user completes their details and the form is valid this callback will
     * be used to communicate to the parent that teh focus needs to change
     */
    public interface Listener {
        void onBillingCompleted();

        void onBillingCanceled();
    }

    /**
     * The callback is used to communicate with the name input
     * <p>
     * The custom {@link DefaultInput} takes care takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private final DefaultInput.Listener mNameListener = new DefaultInput.Listener() {
        @Override
        public void onInputFinish(String value) {
            mDatastore.setCustomerName(value);
        }

        @Override
        public void clearInputError() {
            mNameLayout.setError(null);
            mNameLayout.setErrorEnabled(false);
        }

    };

    /**
     * The callback is used to communicate with the country input
     * <p>
     * The custom {@link CountryInput} takes care of populating the values in the spinner
     * and will trigger this callback when the user selects a new option. State is update
     * accordingly. Moreover, the phone prefix is added bade on the country selected.
     */
    private final CountryInput.CountryListener mCountryListener = new CountryInput.CountryListener() {
        @Override
        public void onCountryInputFinish(String country, String prefix) {
            if (!country.equals("")) {
                mDatastore.setCustomerCountry(country);
            }
            if (!prefix.equals("")) {
                mDatastore.setCustomerPhonePrefix(prefix);
            }
            mPhone.setText(prefix + " " + mDatastore.getCustomerPhone());
            mPhone.requestFocus();
            mPhone.performClick();
            mPhone.setSelection(mPhone.getText().length());
        }
    };

    /**
     * The callback is used to communicate with the address one  input
     * <p>
     * The custom {@link AddressOneInput} takes care takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private final AddressOneInput.AddressOneListener mAddressOneListener = new AddressOneInput.AddressOneListener() {

        @Override
        public void onAddressOneInputFinish(String value) {
            mDatastore.setCustomerAddress1(value);
        }

        @Override
        public void clearAddressOneError() {
            mAddressOneLayout.setError(null);
            mAddressOneLayout.setErrorEnabled(false);
        }
    };

    /**
     * The callback is used to communicate with the address two input
     * <p>
     * The custom {@link DefaultInput} takes care takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private final DefaultInput.Listener mAddressTwoListener = new DefaultInput.Listener() {
        @Override
        public void onInputFinish(String value) {
            mDatastore.setCustomerAddress2(value);
        }

        @Override
        public void clearInputError() {
            mAddressTwoLayout.setError(null);
            mAddressTwoLayout.setErrorEnabled(false);
        }
    };

    /**
     * The callback is used to communicate with the city input
     * <p>
     * The custom {@link DefaultInput} takes care takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private final DefaultInput.Listener mCityListener = new DefaultInput.Listener() {
        @Override
        public void onInputFinish(String value) {
            mDatastore.setCustomerCity(value);
        }

        @Override
        public void clearInputError() {
            mCityLayout.setError(null);
            mCityLayout.setErrorEnabled(false);
        }
    };

    /**
     * The callback is used to communicate with the state input
     * <p>
     * The custom {@link DefaultInput} takes care takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private final DefaultInput.Listener mStateListener = new DefaultInput.Listener() {
        @Override
        public void onInputFinish(String value) {
            mDatastore.setCustomerState(value);
        }

        @Override
        public void clearInputError() {
            mStateLayout.setError(null);
            mStateLayout.setErrorEnabled(false);
        }

    };

    /**
     * The callback is used to communicate with the zip input
     * <p>
     * The custom {@link DefaultInput} takes care takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private final DefaultInput.Listener mZipListener = new DefaultInput.Listener() {
        @Override
        public void onInputFinish(String value) {
            mDatastore.setCustomerZipcode(value);
        }

        @Override
        public void clearInputError() {
            mZipLayout.setError(null);
            mZipLayout.setErrorEnabled(false);
        }
    };

    /**
     * The callback is used to communicate with the phone input
     * <p>
     * The custom {@link PhoneInput} takes care takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private final PhoneInput.PhoneListener mPhoneListener = new PhoneInput.PhoneListener() {
        @Override
        public void onPhoneInputFinish(String phone) {
            mDatastore
                    .setCustomerPhone(phone.replace(mDatastore.getCustomerPhonePrefix(), "")
                            .replaceAll("\\D", ""));
        }

        @Override
        public void clearPhoneError() {
            mPhoneLayout.setError(null);
            mPhoneLayout.setErrorEnabled(false);
        }
    };

    @Nullable
    private BillingDetailsView.Listener mListener;
    @NonNull
    private final DataStore mDatastore = DataStore.getInstance();

    private Button mDone;
    private Button mClear;
    private Toolbar mToolbar;
    private DefaultInput mName;
    private TextInputLayout mNameLayout;
    private CountryInput mCountryInput;
    private AddressOneInput mAddressOne;
    private DefaultInput mAddressTwo;
    private DefaultInput mCity;
    private DefaultInput mState;
    private DefaultInput mZip;
    private PhoneInput mPhone;
    private TextInputLayout mAddressOneLayout;
    private TextInputLayout mAddressTwoLayout;
    private TextInputLayout mCityLayout;
    private TextInputLayout mStateLayout;
    private TextInputLayout mZipLayout;
    private TextInputLayout mPhoneLayout;

    public BillingDetailsView(Context context) {
        this(context, null);
    }

    public BillingDetailsView(@Nullable Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialiseView(context);

        // Used to restore state on orientation changes
        repopulateFields();
    }

    /**
     * The UI initialisation
     * <p>
     * Used to initialise element and pass callbacks as well as setting up appropriate listeners
     */
    private void initialiseView(Context context) {
        inflate(context, R.layout.blling_details, this);
        mToolbar = findViewById(R.id.my_toolbar);

        setFocusableInTouchMode(true);

        mAddressOneLayout = findViewById(R.id.address_one_input_layout);
        mAddressTwoLayout = findViewById(R.id.address_two_input_layout);
        mCityLayout = findViewById(R.id.city_input_layout);
        mStateLayout = findViewById(R.id.state_input_layout);
        mZipLayout = findViewById(R.id.zipcode_input_layout);
        mPhoneLayout = findViewById(R.id.phone_input_layout);

        // trigger focus change to the card details view on the toolbar back button press
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean billingDataValid = false;
                if (mDatastore.getLastBillingValidState() != null) {
                    mDatastore.setCustomerName(mDatastore.getLastCustomerNameState());
                    mDatastore.setCustomerAddress1(mDatastore.getLastBillingValidState().getAddress_line1());
                    mDatastore.setCustomerAddress2(mDatastore.getLastBillingValidState().getAddress_line2());
                    mDatastore.setCustomerZipcode(mDatastore.getLastBillingValidState().getZip());
                    mDatastore.setCustomerCountry(mDatastore.getLastBillingValidState().getCountry());
                    mDatastore.setCustomerCity(mDatastore.getLastBillingValidState().getCity());
                    mDatastore.setCustomerState(mDatastore.getLastBillingValidState().getState());

                    if (mDatastore.getLastPhoneValidState() != null) {
                        mDatastore.setCustomerPhonePrefix(mDatastore.getLastPhoneValidState().getCountry_code());
                        mDatastore.setCustomerPhone(mDatastore.getLastPhoneValidState().getNumber());
                    }
                    billingDataValid = true;
                }

                if (billingDataValid) {
                    repopulateFields();
                    if (mListener != null) mListener.onBillingCompleted();
                } else {
                    if (mListener != null) mListener.onBillingCanceled();
                }
            }
        });

        mName = findViewById(R.id.name_input);
        mNameLayout = findViewById(R.id.name_input_layout);
        mName.setListener(mNameListener);

        mCountryInput = findViewById(R.id.country_input);
        mCountryInput.setCountryListener(mCountryListener);

        mAddressOne = findViewById(R.id.address_one_input);
        mAddressOne.setAddressOneListener(mAddressOneListener);

        mAddressTwo = findViewById(R.id.address_two_input);
        mAddressTwo.setListener(mAddressTwoListener);

        mCity = findViewById(R.id.city_input);
        mCity.setListener(mCityListener);

        mState = findViewById(R.id.state_input);
        mState.setListener(mStateListener);

        mZip = findViewById(R.id.zipcode_input);
        mZip.setListener(mZipListener);

        mPhone = findViewById(R.id.phone_input);
        mPhone.setPhoneListener(mPhoneListener);

        mClear = findViewById(R.id.clear_button);
        if (mDatastore.getClearButtonText() != null) {
            mClear.setText(mDatastore.getClearButtonText());
        }
        if (mDatastore.getClearButtonLayout() != null) {
            mClear.setLayoutParams(mDatastore.getClearButtonLayout());
        }

        mDone = findViewById(R.id.done_button);
        if (mDatastore.getDoneButtonText() != null) {
            mDone.setText(mDatastore.getDoneButtonText());
        }
        if (mDatastore.getDoneButtonLayout() != null) {
            mDone.setLayoutParams(mDatastore.getDoneButtonLayout());
        }

        // Clear the state and the fields if the user chooses to press the clear button
        mClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mName.setText("");
                mNameLayout.setError(null);
                mNameLayout.setErrorEnabled(false);
                if (mDatastore.getDefaultCountry() != null) {
                    int countryPosition = getCountryPositionForLocale(mDatastore.getDefaultCountry());
                    mCountryInput.setSelection(Math.max(countryPosition, 0));
                    mDatastore.setCustomerCountry(mDatastore.getDefaultCountry().getCountry());
                    mDatastore.setCustomerPhonePrefix(PhoneUtils.getPrefix(mDatastore.getDefaultCountry()
                            .getCountry()));
                } else {
                    mCountryInput.setSelection(0);
                }
                ((TextView) mCountryInput.getSelectedView()).setError(null);
                mAddressOne.setText("");
                mAddressOneLayout.setError(null);
                mAddressOneLayout.setErrorEnabled(false);
                mAddressTwo.setText("");
                mAddressTwoLayout.setError(null);
                mAddressTwoLayout.setErrorEnabled(false);
                mCity.setText("");
                mCityLayout.setError(null);
                mCityLayout.setErrorEnabled(false);
                mState.setText("");
                mStateLayout.setError(null);
                mStateLayout.setErrorEnabled(false);
                mZip.setText("");
                mZipLayout.setError(null);
                mZipLayout.setErrorEnabled(false);
                if (mDatastore.getDefaultCountry() != null) {
                    mPhone.setText(PhoneUtils.getPrefix(mDatastore.getDefaultCountry().getCountry()) + " ");
                } else {
                    mPhone.setText("");
                }
                mPhoneLayout.setError(null);
                mPhoneLayout.setErrorEnabled(false);
                mDatastore.cleanBillingData();
                if (mListener != null) {
                    mListener.onBillingCanceled();
                }
                mDatastore.setBillingCompleted(false);
            }
        });

        // Is the form is valid indicate the billing was completed using the callback
        // so the billing spinner can be updated adn teh focus can be changes
        mDone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidForm()) {
                    if (mListener != null) {
                        mDatastore.setBillingCompleted(true);
                        mDatastore.setLastCustomerNameState(mDatastore.getCustomerName());
                        mDatastore.setLastBillingValidState(new BillingModel(
                                mDatastore.getCustomerAddress1(),
                                mDatastore.getCustomerAddress2(),
                                mDatastore.getCustomerZipcode(),
                                mDatastore.getCustomerCountry(),
                                mDatastore.getCustomerCity(),
                                mDatastore.getCustomerState()
                        ));
                        mDatastore.setLastPhoneValidState(new PhoneModel(
                                mDatastore.getCustomerPhonePrefix(),
                                mDatastore.getCustomerPhone()
                        ));
                        mListener.onBillingCompleted();
                    }
                }
            }
        });

        requestFocus();

        // Set custom labels
        if (mDatastore.getCardHolderLabel() != null) {
            mNameLayout.setHint(mDatastore.getCardHolderLabel());
        }
        if (mDatastore.getAddressLine1Label() != null) {
            mAddressOneLayout.setHint(mDatastore.getAddressLine1Label());
        }
        if (mDatastore.getAddressLine2Label() != null) {
            mAddressTwoLayout.setHint(mDatastore.getAddressLine2Label());
        }
        if (mDatastore.getTownLabel() != null) {
            mCityLayout.setHint(mDatastore.getTownLabel());
        }
        if (mDatastore.getStateLabel() != null) {
            mStateLayout.setHint(mDatastore.getStateLabel());
        }
        if (mDatastore.getPostCodeLabel() != null) {
            mZipLayout.setHint(mDatastore.getPostCodeLabel());
        }
        if (mDatastore.getPhoneLabel() != null) {
            mPhoneLayout.setHint(mDatastore.getPhoneLabel());
        }
    }

    /**
     * Used to restore state on orientation changes
     * <p>
     * The method will repopulate all the card input fields with the latest state they were in
     * if the device orientation changes, and therefore avoiding the text inputs to be cleared.
     */
    private void repopulateFields() {
        // Repopulate name
        mName.setText(mDatastore.getCustomerName());

        // Repopulate country
        int countryPosition = getCountryPositionForCountryIdentifier(mDatastore.getCustomerCountry());
        mCountryInput.setSelection(Math.max(countryPosition, 0));

        // Repopulate address line 1
        mAddressOne.setText(mDatastore.getCustomerAddress1());

        // Repopulate address line 1
        mAddressTwo.setText(mDatastore.getCustomerAddress2());

        // Repopulate city
        mCity.setText(mDatastore.getCustomerCity());

        // Repopulate state
        mState.setText(mDatastore.getCustomerState());

        // Repopulate zip/post code
        mZip.setText(mDatastore.getCustomerZipcode());

        // Repopulate phone
        mPhone.setText(mDatastore.getCustomerPhone());
    }

    /**
     * Used to indicate the validity of the billing details from
     * <p>
     * The method will check if the inputs are valid.
     * This method will also populate the field error accordingly
     *
     * @return boolean abut form validity
     */
    private boolean isValidForm() {
        boolean result = true;

        if (mName.length() < 3) {
            mNameLayout.setError(getResources().getString(R.string.error_name));
            result = false;
        }

        if (mCountryInput.getSelectedItemPosition() == 0) {
            ((TextView) mCountryInput.getSelectedView()).setError(getResources().getString(R.string.error_country));
            result = false;
        }
        if (mAddressOne.length() < 3) {
            mAddressOneLayout.setError(getResources().getString(R.string.error_address_one));
            result = false;
        }

        if (mCity.length() < 2) {
            mCityLayout.setError(getResources().getString(R.string.error_city));
            result = false;
        }

        if (mState.length() < 3) {
            mStateLayout.setError(getResources().getString(R.string.error_state));
            result = false;
        }

        if (mZip.length() < 3) {
            mZipLayout.setError(getResources().getString(R.string.error_postcode));
            result = false;
        }

        if (mPhone.length() < 3) {
            mPhoneLayout.setError(getResources().getString(R.string.error_phone));
            result = false;
        }

        return result;
    }

    /**
     * Used to clear the text and state of the fields
     */
    public void resetFields() {
        resetAllErrorIndicators();

        if (mDatastore.getDefaultCustomerName() != null) {
            mName.setText(mDatastore.getDefaultCustomerName());
        } else {
            mName.setText("");
        }

        // Repopulate country
        if (mDatastore.getDefaultCountry() != null) {
            int countryPosition = getCountryPositionForLocale(mDatastore.getDefaultCountry());
            mCountryInput.setSelection(Math.max(countryPosition, 0));
            mDatastore.setCustomerCountry(mDatastore.getDefaultCountry().getCountry());
            mDatastore.setCustomerPhonePrefix(PhoneUtils.getPrefix(mDatastore.getDefaultCountry()
                    .getCountry()));
        } else {
            mCountryInput.setSelection(0);
        }

        if (mDatastore.getDefaultBillingDetails() != null &&
                mDatastore.getDefaultCountry() != null &&
                mDatastore.getCustomerPhone() != null) {
            mPhone.setText(PhoneUtils.getPrefix(mDatastore.getDefaultCountry().getCountry()) +
                    " " + mDatastore.getCustomerPhone());
            mAddressOne.setText(mDatastore.getDefaultBillingDetails().getAddress_line1());
            mAddressTwo.setText(mDatastore.getDefaultBillingDetails().getAddress_line2());
            mCity.setText(mDatastore.getDefaultBillingDetails().getCity());
            mState.setText(mDatastore.getDefaultBillingDetails().getState());
            mStateLayout.setError(null);
            mZip.setText(mDatastore.getDefaultBillingDetails().getZip());
        } else {
            // Reset phone prefix
            if (mDatastore.getDefaultCountry() != null) {
                mPhone.setText(PhoneUtils.getPrefix(mDatastore.getDefaultCountry().getCountry()) + " ");
            } else {
                mPhone.setText("");
            }
            ((TextView) mCountryInput.getSelectedView()).setError(null);
            mAddressOne.setText("");
            mAddressTwo.setText("");
            mCity.setText("");
            mState.setText("");
            mZip.setText("");
        }
    }

    public void resetAllErrorIndicators() {
        mNameLayout.setError(null);
        mNameLayout.setErrorEnabled(false);

        mAddressOneLayout.setError(null);
        mAddressOneLayout.setErrorEnabled(false);
        mAddressTwoLayout.setError(null);
        mAddressTwoLayout.setErrorEnabled(false);
        mCityLayout.setError(null);
        mCityLayout.setErrorEnabled(false);
        mStateLayout.setError(null);
        mStateLayout.setErrorEnabled(false);
        mZipLayout.setError(null);
        mZipLayout.setErrorEnabled(false);

        mPhoneLayout.setError(null);
        mPhoneLayout.setErrorEnabled(false);
    }

    // Move to previous view on back button pressed
    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_DOWN) {
            return false;
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            // Prevent back button to trigger the mListener is any is focused
            if (mListener != null &&
                    !mAddressOne.hasFocus() &&
                    !mName.hasFocus() &&
                    !mAddressTwo.hasFocus() &&
                    !mCity.hasFocus() &&
                    !mState.hasFocus() &&
                    !mZip.hasFocus() &&
                    !mPhone.hasFocus()) {
                if (mDatastore.getLastBillingValidState() != null) {
                    mDatastore.setCustomerName(mDatastore.getLastCustomerNameState());
                    mDatastore.setCustomerAddress1(mDatastore.getLastBillingValidState().getAddress_line1());
                    mDatastore.setCustomerAddress2(mDatastore.getLastBillingValidState().getAddress_line2());
                    mDatastore.setCustomerZipcode(mDatastore.getLastBillingValidState().getZip());
                    mDatastore.setCustomerCountry(mDatastore.getLastBillingValidState().getCountry());
                    mDatastore.setCustomerCity(mDatastore.getLastBillingValidState().getCity());
                    mDatastore.setCustomerState(mDatastore.getLastBillingValidState().getState());
                    mDatastore.setCustomerPhonePrefix(mDatastore.getLastPhoneValidState().getCountry_code());
                    mDatastore.setCustomerPhone(mDatastore.getLastPhoneValidState().getNumber());
                    repopulateFields();
                    mListener.onBillingCompleted();
                } else {
                    mListener.onBillingCanceled();
                }
                return true;
            } else {
                requestFocus();
                return false;
            }
        }

        return super.dispatchKeyEventPreIme(event);
    }


    /**
     * Used to set the callback listener for when the card details page is requested
     */
    public void setGoToCardDetailsListener(BillingDetailsView.Listener listener) {
        mListener = listener;
    }


    private int getCountryPositionForLocale(@NonNull Locale locale) {
        for (int position = 0; position < mCountryInput.getAdapter().getCount(); position++) {
            if (locale.getDisplayCountry().equals(mCountryInput.getAdapter().getItem(position))) {
                return position;
            }
        }

        return -1;
    }

    private int getCountryPositionForCountryIdentifier(String countryIdentifier) {
        if (countryIdentifier == null) return  -1;

        Locale[] availableLocales = Locale.getAvailableLocales();
        for (Locale loc : availableLocales) {
            if (loc.getCountry().equals(countryIdentifier)) {
                int countryPosition = getCountryPositionForLocale(loc);
                if (countryPosition > 0) {
                    return countryPosition;
                }
            }
        }

        return -1;
    }
}
