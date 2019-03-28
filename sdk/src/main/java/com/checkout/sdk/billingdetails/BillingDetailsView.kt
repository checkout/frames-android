package com.checkout.sdk.billingdetails

import android.content.Context
import android.support.design.widget.TextInputLayout
import android.util.AttributeSet
import android.view.KeyEvent
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.checkout.sdk.R
import com.checkout.sdk.input.AddressOneInput
import com.checkout.sdk.input.CountryInput
import com.checkout.sdk.input.DefaultInput
import com.checkout.sdk.input.PhoneInput
import com.checkout.sdk.models.BillingModel
import com.checkout.sdk.models.PhoneModel
import com.checkout.sdk.store.DataStore
import com.checkout.sdk.utils.PhoneUtils
import java.util.*

/**
 * The controller of the billing details view page
 *
 *
 * This class handles interaction with the custom inputs in the billing details form.
 * The state of the view is handled here, so are action like focus changes, full form
 * validation, listeners, persistence over orientation.
 */
class BillingDetailsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    /**
     * The callback is used to communicate with the name input
     *
     *
     * The custom [DefaultInput] takes care takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private val mNameListener = object : DefaultInput.Listener {
        override fun onInputFinish(value: String) {
            mDatastore.customerName = value
        }

        override fun clearInputError() {
            mNameLayout!!.error = null
            mNameLayout!!.isErrorEnabled = false
        }
    }

    /**
     * The callback is used to communicate with the country input
     *
     *
     * The custom [CountryInput] takes care of populating the values in the spinner
     * and will trigger this callback when the user selects a new option. State is update
     * accordingly. Moreover, the phone prefix is added bade on the country selected.
     */
    private val mCountryListener = CountryInput.CountryListener { country, prefix ->
        if (country != "") {
            mDatastore.customerCountry = country
        }
        if (prefix != "") {
            mDatastore.customerPhonePrefix = prefix
        }
        mPhone!!.setText(prefix + " " + mDatastore.customerPhone)
        mPhone!!.requestFocus()
        mPhone!!.performClick()
        mPhone!!.setSelection(mPhone!!.text.length)
    }

    /**
     * The callback is used to communicate with the address one  input
     *
     *
     * The custom [AddressOneInput] takes care takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private val mAddressOneListener = object : AddressOneInput.AddressOneListener {

        override fun onAddressOneInputFinish(value: String) {
            mDatastore.customerAddress1 = value
        }

        override fun clearAddressOneError() {
            mAddressOneLayout!!.error = null
            mAddressOneLayout!!.isErrorEnabled = false
        }
    }

    /**
     * The callback is used to communicate with the address two input
     *
     *
     * The custom [DefaultInput] takes care takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private val mAddressTwoListener = object : DefaultInput.Listener {
        override fun onInputFinish(value: String) {
            mDatastore.customerAddress2 = value
        }

        override fun clearInputError() {
            mAddressTwoLayout!!.error = null
            mAddressTwoLayout!!.isErrorEnabled = false
        }
    }

    /**
     * The callback is used to communicate with the city input
     *
     *
     * The custom [DefaultInput] takes care takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private val mCityListener = object : DefaultInput.Listener {
        override fun onInputFinish(value: String) {
            mDatastore.customerCity = value
        }

        override fun clearInputError() {
            mCityLayout!!.error = null
            mCityLayout!!.isErrorEnabled = false
        }
    }

    /**
     * The callback is used to communicate with the state input
     *
     *
     * The custom [DefaultInput] takes care takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private val mStateListener = object : DefaultInput.Listener {
        override fun onInputFinish(value: String) {
            mDatastore.customerState = value
        }

        override fun clearInputError() {
            mStateLayout!!.error = null
            mStateLayout!!.isErrorEnabled = false
        }

    }

    /**
     * The callback is used to communicate with the zip input
     *
     *
     * The custom [DefaultInput] takes care takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private val mZipListener = object : DefaultInput.Listener {
        override fun onInputFinish(value: String) {
            mDatastore.customerZipcode = value
        }

        override fun clearInputError() {
            mZipLayout!!.error = null
            mZipLayout!!.isErrorEnabled = false
        }
    }

    /**
     * The callback is used to communicate with the phone input
     *
     *
     * The custom [PhoneInput] takes care takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private val mPhoneListener = object : PhoneInput.PhoneListener {
        override fun onPhoneInputFinish(phone: String) {
            mDatastore
                .customerPhone = phone.replace(mDatastore.customerPhonePrefix, "")
                .replace("\\D".toRegex(), "")
        }

        override fun clearPhoneError() {
            mPhoneLayout!!.error = null
            mPhoneLayout!!.isErrorEnabled = false
        }
    }

    private var mListener: BillingDetailsView.Listener? = null
    private val mDone: Button? = null
    private var mClear: Button? = null
    private var mToolbar: android.support.v7.widget.Toolbar? = null
    private var mName: DefaultInput? = null
    private var mNameLayout: TextInputLayout? = null
    private var mCountryInput: CountryInput? = null
    private var mAddressOne: AddressOneInput? = null
    private var mAddressTwo: DefaultInput? = null
    private var mCity: DefaultInput? = null
    private var mState: DefaultInput? = null
    private var mZip: DefaultInput? = null
    private var mPhone: PhoneInput? = null
    private val mDatastore = DataStore.Factory.get()
    private var mAddressOneLayout: TextInputLayout? = null
    private var mAddressTwoLayout: TextInputLayout? = null
    private var mCityLayout: TextInputLayout? = null
    private var mStateLayout: TextInputLayout? = null
    private var mZipLayout: TextInputLayout? = null
    private var mPhoneLayout: TextInputLayout? = null

    /**
     * Used to indicate the validity of the billing details from
     *
     *
     * The method will check if the inputs are valid.
     * This method will also populate the field error accordingly
     *
     * @return boolean abut form validity
     */
    private val isValidForm: Boolean
        get() {
            var result = true

            if (mName!!.length() < 3) {
                mNameLayout!!.error = resources.getString(R.string.error_name)
                result = false
            }

            if (mCountryInput!!.selectedItemPosition == 0) {
                (mCountryInput!!.selectedView as TextView).error =
                        resources.getString(R.string.error_country)
                result = false
            }
            if (mAddressOne!!.length() < 3) {
                mAddressOneLayout!!.error = resources.getString(R.string.error_address_one)
                result = false
            }

            if (mCity!!.length() < 2) {
                mCityLayout!!.error = resources.getString(R.string.error_city)
                result = false
            }

            if (mState!!.length() < 3) {
                mStateLayout!!.error = resources.getString(R.string.error_state)
                result = false
            }

            if (mZip!!.length() < 3) {
                mZipLayout!!.error = resources.getString(R.string.error_postcode)
                result = false
            }

            if (mPhone!!.length() < 3) {
                mPhoneLayout!!.error = resources.getString(R.string.error_phone)
                result = false
            }

            return result
        }

    /**
     * The callback used to indicate is the billing details were completed
     *
     *
     * After the user completes their details and the form is valid this callback will
     * be used to communicate to the parent that teh focus needs to change
     */
    interface Listener {
        fun onBillingCompleted()

        fun onBillingCanceled()
    }

    init {
        init()
    }

    /**
     * The UI initialisation
     *
     *
     * Used to initialise element and pass callbacks as well as setting up appropriate listeners
     */
    private fun init() {
        inflate(context, R.layout.billing_details, this)
        orientation = LinearLayout.VERTICAL
        mToolbar = findViewById(R.id.my_toolbar)

        isFocusableInTouchMode = true

        mAddressOneLayout = findViewById(R.id.address_one_input_layout)
        mAddressTwoLayout = findViewById(R.id.address_two_input_layout)
        mCityLayout = findViewById(R.id.city_input_layout)
        mStateLayout = findViewById(R.id.state_input_layout)
        mZipLayout = findViewById(R.id.zipcode_input_layout)
        mPhoneLayout = findViewById(R.id.phone_input_layout)

        // trigger focus change to the card details view on the toolbar back button press
        mToolbar!!.setNavigationOnClickListener {
            if (mDatastore.lastBillingValidState != null) {
                mDatastore.customerName = mDatastore.lastCustomerNameState!!
                mDatastore.customerAddress1 = mDatastore.lastBillingValidState!!.addressLine1
                mDatastore.customerAddress2 = mDatastore.lastBillingValidState!!.addressLine2
                mDatastore.customerZipcode = mDatastore.lastBillingValidState!!.postcode
                mDatastore.customerCountry = mDatastore.lastBillingValidState!!.country
                mDatastore.customerCity = mDatastore.lastBillingValidState!!.city
                mDatastore.customerState = mDatastore.lastBillingValidState!!.state
                mDatastore.customerPhonePrefix =
                        mDatastore.lastBillingValidState!!.phone.countryCode
                mDatastore.customerPhone = mDatastore.lastBillingValidState!!.phone.number
                repopulateFields()
                mListener!!.onBillingCompleted()
            } else {
                mListener!!.onBillingCanceled()
            }
        }

        mName = findViewById(R.id.name_input)
        mNameLayout = findViewById(R.id.name_input_layout)
        mName!!.setListener(mNameListener)

        mCountryInput = findViewById(R.id.country_input)
        mCountryInput!!.setCountryListener(mCountryListener)

        mAddressOne = findViewById(R.id.address_one_input)
        mAddressOne!!.setAddressOneListener(mAddressOneListener)

        mAddressTwo = findViewById(R.id.address_two_input)
        mAddressTwo!!.setListener(mAddressTwoListener)

        mCity = findViewById(R.id.city_input)
        mCity!!.setListener(mCityListener)

        mState = findViewById(R.id.state_input)
        mState!!.setListener(mStateListener)

        mZip = findViewById(R.id.zipcode_input)
        mZip!!.setListener(mZipListener)

        mPhone = findViewById(R.id.phone_input)
        mPhone!!.setPhoneListener(mPhoneListener)

        mClear = findViewById(R.id.clear_button)

        // Used to restore state on orientation changes
        repopulateFields()

        // Clear the state and the fields if the user chooses to press the clear button
        mClear!!.setOnClickListener {
            mName!!.setText("")
            mNameLayout!!.error = null
            mNameLayout!!.isErrorEnabled = false
            if (mDatastore.defaultCountry != null) {
                mCountryInput!!.setSelection(
                    (mCountryInput!!.adapter as ArrayAdapter<String>)
                        .getPosition(mDatastore.defaultCountry!!.displayCountry)
                )
                mDatastore.customerCountry = mDatastore.defaultCountry!!.country
                mDatastore.customerPhonePrefix = PhoneUtils.getPrefix(
                    mDatastore.defaultCountry!!
                        .country
                )
            } else {
                mCountryInput!!.setSelection(0)
            }
            (mCountryInput!!.selectedView as TextView).error = null
            mAddressOne!!.setText("")
            mAddressOneLayout!!.error = null
            mAddressOneLayout!!.isErrorEnabled = false
            mAddressTwo!!.setText("")
            mAddressTwoLayout!!.error = null
            mAddressTwoLayout!!.isErrorEnabled = false
            mCity!!.setText("")
            mCityLayout!!.error = null
            mCityLayout!!.isErrorEnabled = false
            mState!!.setText("")
            mStateLayout!!.error = null
            mStateLayout!!.isErrorEnabled = false
            mZip!!.setText("")
            mZipLayout!!.error = null
            mZipLayout!!.isErrorEnabled = false
            if (mDatastore.defaultCountry != null) {
                mPhone!!.setText(PhoneUtils.getPrefix(mDatastore.defaultCountry!!.country) + " ")
            } else {
                mPhone!!.setText("")
            }
            mPhoneLayout!!.error = null
            mPhoneLayout!!.isErrorEnabled = false
            mDatastore.cleanBillingData()
            if (mListener != null) {
                mListener!!.onBillingCanceled()
            }
            mDatastore.isBillingCompleted = false
        }

        // Is the form is valid indicate the billing was completed using the callback
        // so the billing spinner can be updated adn teh focus can be changes
        mDone!!.setOnClickListener {
            if (isValidForm) {
                if (mListener != null) {
                    mDatastore.isBillingCompleted = true
                    mDatastore.lastCustomerNameState = mDatastore.customerName
                    mDatastore.lastBillingValidState = BillingModel(
                        mDatastore.customerAddress1,
                        mDatastore.customerAddress2,
                        mDatastore.customerZipcode,
                        mDatastore.customerCountry,
                        mDatastore.customerCity,
                        mDatastore.customerState,
                        PhoneModel(
                            mDatastore.customerPhonePrefix,
                            mDatastore.customerPhone
                        )
                    )
                    mListener!!.onBillingCompleted()
                }
            }
        }

        requestFocus()

        // Set custom labels
        if (mDatastore.cardHolderLabel != null) {
            mNameLayout!!.hint = mDatastore.cardHolderLabel
        }
        if (mDatastore.addressLine1Label != null) {
            mAddressOneLayout!!.hint = mDatastore.addressLine1Label
        }
        if (mDatastore.addressLine2Label != null) {
            mAddressTwoLayout!!.hint = mDatastore.addressLine2Label
        }
        if (mDatastore.townLabel != null) {
            mCityLayout!!.hint = mDatastore.townLabel
        }
        if (mDatastore.stateLabel != null) {
            mStateLayout!!.hint = mDatastore.stateLabel
        }
        if (mDatastore.postCodeLabel != null) {
            mZipLayout!!.hint = mDatastore.postCodeLabel
        }
        if (mDatastore.phoneLabel != null) {
            mPhoneLayout!!.hint = mDatastore.phoneLabel
        }
    }

    /**
     * Used to restore state on orientation changes
     *
     *
     * The method will repopulate all the card input fields with the latest state they were in
     * if the device orientation changes, and therefore avoiding the text inputs to be cleared.
     */
    private fun repopulateFields() {
        // Repopulate name
        mName!!.setText(mDatastore.customerName)

        // Repopulate country
        val locale = Locale.getAvailableLocales()
        var country: String

        for (loc in locale) {
            country = loc.displayCountry
            if (loc.country == mDatastore.customerCountry) {
                mCountryInput!!.setSelection(
                    (mCountryInput!!.adapter as ArrayAdapter<String>)
                        .getPosition(country)
                )
            }
        }

        // Repopulate address line 1
        mAddressOne!!.setText(mDatastore.customerAddress1)

        // Repopulate address line 1
        mAddressTwo!!.setText(mDatastore.customerAddress2)

        // Repopulate city
        mCity!!.setText(mDatastore.customerCity)

        // Repopulate state
        mState!!.setText(mDatastore.customerState)

        // Repopulate zip/post code
        mZip!!.setText(mDatastore.customerZipcode)

        // Repopulate phone
        mPhone!!.setText(mDatastore.customerPhone)
    }

    /**
     * Used to clear the text and state of the fields
     */
    fun resetFields() {
        if (mDatastore.defaultCustomerName != null) {
            mName!!.setText(mDatastore.defaultCustomerName)
            mNameLayout!!.error = null
            mNameLayout!!.isErrorEnabled = false
        } else {
            mName!!.setText("")
            mNameLayout!!.error = null
            mNameLayout!!.isErrorEnabled = false
        }
        // Repopulate country
        if (mDatastore.defaultCountry != null) {
            mCountryInput!!.setSelection(
                (mCountryInput!!.adapter as ArrayAdapter<String>)
                    .getPosition(mDatastore.defaultCountry!!.displayCountry)
            )
            mDatastore.customerCountry = mDatastore.defaultCountry!!.country
            mDatastore.customerPhonePrefix = PhoneUtils.getPrefix(
                mDatastore.defaultCountry!!
                    .country
            )
        } else {
            mCountryInput!!.setSelection(0)
        }

        if (mDatastore.defaultBillingDetails != null &&
            mDatastore.defaultCountry != null &&
            mDatastore.customerPhone != null
        ) {
            mPhone!!.setText(
                PhoneUtils.getPrefix(mDatastore.defaultCountry!!.country) +
                        " " + mDatastore.customerPhone
            )
            mAddressOne!!.setText(mDatastore.defaultBillingDetails!!.addressLine1)
            mAddressOneLayout!!.error = null
            mAddressOneLayout!!.isErrorEnabled = false
            mAddressTwo!!.setText(mDatastore.defaultBillingDetails!!.addressLine2)
            mAddressTwoLayout!!.error = null
            mAddressTwoLayout!!.isErrorEnabled = false
            mCity!!.setText(mDatastore.defaultBillingDetails!!.city)
            mCityLayout!!.error = null
            mCityLayout!!.isErrorEnabled = false
            mState!!.setText(mDatastore.defaultBillingDetails!!.state)
            mStateLayout!!.error = null
            mStateLayout!!.isErrorEnabled = false
            mZip!!.setText(mDatastore.defaultBillingDetails!!.postcode)
            mZipLayout!!.error = null
            mZipLayout!!.isErrorEnabled = false
            mPhoneLayout!!.error = null
            mPhoneLayout!!.isErrorEnabled = false
        } else {
            // Reset phone prefix
            if (mDatastore.defaultCountry != null) {
                mPhone!!.setText(PhoneUtils.getPrefix(mDatastore.defaultCountry!!.country) + " ")
            } else {
                mPhone!!.setText("")
            }
            (mCountryInput!!.selectedView as TextView).error = null
            mAddressOne!!.setText("")
            mAddressOneLayout!!.error = null
            mAddressOneLayout!!.isErrorEnabled = false
            mAddressTwo!!.setText("")
            mAddressTwoLayout!!.error = null
            mAddressTwoLayout!!.isErrorEnabled = false
            mCity!!.setText("")
            mCityLayout!!.error = null
            mCityLayout!!.isErrorEnabled = false
            mState!!.setText("")
            mStateLayout!!.error = null
            mStateLayout!!.isErrorEnabled = false
            mZip!!.setText("")
            mZipLayout!!.error = null
            mZipLayout!!.isErrorEnabled = false
            mPhoneLayout!!.error = null
            mPhoneLayout!!.isErrorEnabled = false
        }
    }

    // Move to previous view on back button pressed
    override fun dispatchKeyEventPreIme(event: KeyEvent): Boolean {
        if (event.action != KeyEvent.ACTION_DOWN) {
            return false
        }
        if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            // Prevent back button to trigger the mListener is any is focused
            if (mListener != null &&
                !mAddressOne!!.hasFocus() &&
                !mName!!.hasFocus() &&
                !mAddressTwo!!.hasFocus() &&
                !mCity!!.hasFocus() &&
                !mState!!.hasFocus() &&
                !mZip!!.hasFocus() &&
                !mPhone!!.hasFocus()
            ) {
                if (mDatastore.lastBillingValidState != null) {
                    mDatastore.customerName = mDatastore.lastCustomerNameState!!
                    mDatastore.customerAddress1 = mDatastore.lastBillingValidState!!.addressLine1
                    mDatastore.customerAddress2 = mDatastore.lastBillingValidState!!.addressLine2
                    mDatastore.customerZipcode = mDatastore.lastBillingValidState!!.postcode
                    mDatastore.customerCountry = mDatastore.lastBillingValidState!!.country
                    mDatastore.customerCity = mDatastore.lastBillingValidState!!.city
                    mDatastore.customerState = mDatastore.lastBillingValidState!!.state
                    mDatastore.customerPhonePrefix =
                            mDatastore.lastBillingValidState!!.phone.countryCode
                    mDatastore.customerPhone = mDatastore.lastBillingValidState!!.phone.number
                    repopulateFields()
                    mListener!!.onBillingCompleted()
                } else {
                    mListener!!.onBillingCanceled()
                }
                return true
            } else {
                requestFocus()
                return false
            }
        }

        return super.dispatchKeyEventPreIme(event)
    }


    /**
     * Used to set the callback listener for when the card details page is requested
     */
    fun setGoToCardDetailsListener(listener: BillingDetailsView.Listener) {
        mListener = listener
    }

}
