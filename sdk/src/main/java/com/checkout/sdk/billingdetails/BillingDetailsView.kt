package com.checkout.sdk.billingdetails

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.checkout.sdk.R
import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.architecture.PresenterStore
import com.checkout.sdk.store.DataStore
import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.utils.PhoneUtils
import kotlinx.android.synthetic.main.billing_details.view.*

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
) : LinearLayout(context, attrs),
    MvpView<BillingDetailsUiState> {

    override fun onStateUpdated(uiState: BillingDetailsUiState) {
        if (!uiState.countries.isEmpty() && country_input.adapter == null) {
            val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, uiState.countries)
            country_input.adapter = adapter
        }
        if (country_input.selectedItemPosition != uiState.position) {
            country_input.setSelection(uiState.position)
        }
    }

    private var mListener: BillingDetailsView.Listener? = null
    private val dataStore: DataStore = DataStore.Factory.get()
    private val inMemoryStore: InMemoryStore = InMemoryStore.Factory.get()

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

            val inMemoryStore = InMemoryStore.Factory.get()
            if (!inMemoryStore.customerName.isValid()) {
                result = false
            }

            // TODO: validate inMemoryStore.billingAddress

            if (country_input.selectedItemPosition == 0) {
                (country_input.selectedView as TextView).error =
                        resources.getString(R.string.error_country)
                result = false
            }

            // TODO: Validate Phone number

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

    private var presenter: BillingDetailsPresenter

    init {
        inflate(this.context, R.layout.billing_details, this)
        orientation = VERTICAL
        isFocusableInTouchMode = true
        val positionZeroString = context.getString(R.string.placeholder_country)
        presenter = PresenterStore.getOrCreate(
            BillingDetailsPresenter::class.java,
            { BillingDetailsPresenter(BillingDetailsUiState.create(inMemoryStore, positionZeroString)) })
        presenter.start(this)
        phone_input.listenForRepositoryChange()

        my_toolbar.setNavigationOnClickListener {
            if (dataStore.lastBillingValidState != null) {
                dataStore.customerName = dataStore.lastCustomerNameState!!
                dataStore.customerAddress1 = dataStore.lastBillingValidState!!.addressOne.value
                dataStore.customerAddress2 = dataStore.lastBillingValidState!!.addressTwo.value
                dataStore.customerZipcode = dataStore.lastBillingValidState!!.postcode.value
                dataStore.customerCountry = dataStore.lastBillingValidState!!.country
                dataStore.customerCity = dataStore.lastBillingValidState!!.city.value
                dataStore.customerState = dataStore.lastBillingValidState!!.state.value
                dataStore.customerPhonePrefix = dataStore.lastBillingValidState!!.phone.countryCode
                dataStore.customerPhone = dataStore.lastBillingValidState!!.phone.number
                mListener?.onBillingCompleted()
            } else {
                mListener?.onBillingCanceled()
            }
        }
        country_input.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val countrySelectedUseCaseBuilder =
                    CountrySelectedUseCase.Builder(inMemoryStore, position)
                presenter.countrySelected(countrySelectedUseCaseBuilder)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Nothing
            }
        }

        clear_button.setOnClickListener {
            name_input.reset()
            if (dataStore.defaultCountry != null) {
                country_input.setSelection(
                    (country_input.adapter as ArrayAdapter<String>)
                        .getPosition(dataStore.defaultCountry!!.displayCountry)
                )
                dataStore.customerCountry = dataStore.defaultCountry!!.country
                dataStore.customerPhonePrefix = PhoneUtils.getPrefix(
                    dataStore.defaultCountry!!.country
                )
            } else {
                country_input.setSelection(0)
            }
            (country_input.selectedView as TextView).error = null
            dataStore.cleanBillingData()
            mListener?.onBillingCanceled()
            dataStore.isBillingCompleted = false
        }
        done_button.setOnClickListener {
            if (isValidForm) {
                // TODO: Use InMemoryStore instead
//                    dataStore.isBillingCompleted = true
//                    dataStore.lastCustomerNameState = dataStore.customerName
//                    dataStore.lastBillingValidState = BillingModel(
//                        dataStore.customerAddress1,
//                        dataStore.customerAddress2,
//                        dataStore.customerZipcode,
//                        dataStore.customerCountry,
//                        dataStore.customerCity,
//                        dataStore.customerState,
//                        PhoneModel(
//                            dataStore.customerPhonePrefix,
//                            dataStore.customerPhone
//                        )
//                    )
//                    mListener?.onBillingCompleted()
            }
        }
        requestFocus()
    }

    /**
     * Used to clear the text and state of the fields
     */
    fun resetFields() {
        name_input.reset()
        address_one_input.reset()
        address_two_input.reset()
        city_input.reset()
        state_input.reset()
        country_input.setSelection(0)
        phone_input.reset()
    }

    // Move to previous view on back button pressed
    override fun dispatchKeyEventPreIme(event: KeyEvent): Boolean {
        if (event.action != KeyEvent.ACTION_DOWN) {
            return false
        }
        if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            // Prevent back button to trigger the mListener is any is focused
            if (mListener != null &&
                !address_one_input.hasFocus() &&
                !name_input.hasFocus() &&
                !address_two_input.hasFocus() &&
                !city_input.hasFocus() &&
                !state_input.hasFocus() &&
                !zipcode_input.hasFocus() &&
                !phone_input.hasFocus()
            ) {
                if (dataStore.lastBillingValidState != null) {
                    dataStore.customerName = dataStore.lastCustomerNameState!!
                    dataStore.customerAddress1 = dataStore.lastBillingValidState!!.addressOne.value
                    dataStore.customerAddress2 = dataStore.lastBillingValidState!!.addressTwo.value
                    dataStore.customerZipcode = dataStore.lastBillingValidState!!.postcode.value
                    dataStore.customerCountry = dataStore.lastBillingValidState!!.country
                    dataStore.customerCity = dataStore.lastBillingValidState!!.city.value
                    dataStore.customerState = dataStore.lastBillingValidState!!.state.value
                    dataStore.customerPhonePrefix =
                            dataStore.lastBillingValidState!!.phone.countryCode
                    dataStore.customerPhone = dataStore.lastBillingValidState!!.phone.number
                    mListener?.onBillingCompleted()
                } else {
                    mListener?.onBillingCanceled()
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
