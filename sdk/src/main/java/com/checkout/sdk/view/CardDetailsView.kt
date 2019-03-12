package com.checkout.sdk.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import com.android.volley.VolleyError
import com.checkout.sdk.CheckoutAPIClient
import com.checkout.sdk.R
import com.checkout.sdk.cardinput.CardInput
import com.checkout.sdk.input.BillingInput
import com.checkout.sdk.models.BillingModel
import com.checkout.sdk.models.PhoneModel
import com.checkout.sdk.monthinput.MonthInput
import com.checkout.sdk.request.CardTokenisationRequest
import com.checkout.sdk.response.CardTokenisationFail
import com.checkout.sdk.response.CardTokenisationResponse
import com.checkout.sdk.store.DataStore
import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.utils.CardUtils
import com.checkout.sdk.utils.DateFormatter
import com.checkout.sdk.yearinput.YearInput
import kotlinx.android.synthetic.main.card_details.view.*
import java.util.*

/**
 * The controller of the card details view page
 *
 *
 * This class handles interaction with the custom inputs in the card details form.
 * The state of the view is handled here, so are action like focus changes, full form
 * validation, listeners, persistence over orientation.
 */
class CardDetailsView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null
) : LinearLayout(mContext, attrs) {

    private val inMemoryStore = InMemoryStore.Factory.get()

    /**
     * The callback is used to communicate with the card input
     *
     *
     * The custom [CardInput] takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private val mCardInputListener = object : CardInput.Listener {
        override fun onCardInputFinish(number: String) {
            mDataStore.isValidCardNumber = true
        }

        override fun onCardError() {
            card_input_layout.error = resources.getString(R.string.error_card_number)
            mDataStore.isValidCardNumber = false
        }

        override fun onClearCardError() {
            card_input_layout.error = null
            card_input_layout.isErrorEnabled = false
        }
    }

    // Callback used for the outcome of the generating a token
    private val mTokenListener = object : CheckoutAPIClient.OnTokenGenerated {
        override fun onTokenGenerated(token: CardTokenisationResponse) {
            mDetailsCompletedListener?.onTokeGenerated(token)
        }

        override fun onError(error: CardTokenisationFail) {
            mDetailsCompletedListener?.onError(error)
        }

        override fun onNetworkError(error: VolleyError) {
            mDetailsCompletedListener?.onNetworkError(error)
        }
    }

    /**
     * The callback is used to trigger the focus change to the billing page
     */
    private val mBillingInputListener = BillingInput.BillingListener {
        mGotoBillingListener?.onGoToBillingPressed()
    }

    internal var mDataStore: DataStore = DataStore.getInstance()
    private var mGotoBillingListener: CardDetailsView.GoToBillingListener? = null
    private var mDetailsCompletedListener: CardDetailsView.DetailsCompleted? = null
    private var mCheckoutAPIClient: CheckoutAPIClient? = null


    private var mAcceptedCardsView: LinearLayout? = null

    /**
     * Used to indicate the validity of the full card from
     *
     *
     * The method will check if the inputs are valid and also check the relation between the inputs
     * to ensure validity (e.g. month to year relation).
     * This method will also populate the field error accordingly
     *
     * @return boolean abut form validity
     */
    private val isValidForm: Boolean
        get() {

            var outcome = true

            checkFullDate()

            if (!mDataStore.isValidCardNumber) {
                card_input_layout.error = resources.getString(R.string.error_card_number)
                outcome = false
            }

            if (!inMemoryStore.cvv.isValid()) {
                cvv_input.error = resources.getString(R.string.error_cvv)
                outcome = false
            } else {
                cvv_input.error = null
                cvv_input.isErrorEnabled = false
            }

            return outcome
        }

    /**
     * The callback used to indicate the form submission
     *
     *
     * After the user completes their details and the form is valid this callback will
     * be used to communicate to the parent and start the necessary API call(s).
     */
    interface DetailsCompleted {
        fun onFormSubmit()
        fun onTokeGenerated(reponse: CardTokenisationResponse)
        fun onError(error: CardTokenisationFail)
        fun onNetworkError(error: VolleyError)
        fun onBackPressed()
    }

    /**
     * The callback used to indicate the view needs to moved to the billing details page
     *
     *
     * When the user selects the option to add billing details this callback will be used
     * to communicate to the parent the focus change is requested
     */
    interface GoToBillingListener {
        fun onGoToBillingPressed()
    }

    /**
     * The UI initialisation
     *
     *
     * Used to initialise element and pass callbacks as well as setting up appropriate listeners
     */
    init {
        View.inflate(mContext, R.layout.card_details, this)

        card_input.setCardListener(mCardInputListener)
        my_toolbar.setNavigationOnClickListener {
            mDetailsCompletedListener?.onBackPressed()
        }

        // Hide billing details options based on the module initialisation option
        if (!mDataStore.billingVisibility) {
            billing_helper_text.visibility = View.GONE
            go_to_billing.visibility = View.GONE
        } else {
            go_to_billing.setBillingListener(mBillingInputListener)
        }

        if (mDataStore.payButtonText != null) {
            pay_button.text = mDataStore.payButtonText
        }
        if (mDataStore.payButtonLayout != null) {
            pay_button.layoutParams = mDataStore.payButtonLayout
        }

        pay_button.setOnClickListener {
            // hide keyboard
            try {
                val imm =
                    mContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(windowToken, 0)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (isValidForm) {
                mDetailsCompletedListener?.onFormSubmit()
                mCheckoutAPIClient = CheckoutAPIClient(
                    context, // context
                    mDataStore.key, // your public key
                    mDataStore.environment
                )
                mCheckoutAPIClient?.setTokenListener(mTokenListener)
                val test = generateRequest()
                mCheckoutAPIClient?.generateToken(test)
            }
        }

        // Restore state in case the orientation changes
        repopulateField()

        // Populate accepted cards
        mAcceptedCardsView = findViewById(R.id.card_icons_layout)
        setAcceptedCards()

        // Set custom labels
        if (mDataStore.acceptedLabel != null) {
            accepted_card_helper.text = mDataStore.acceptedLabel
        }
        if (mDataStore.cardLabel != null) {
            card_input_layout.hint = mDataStore.cardLabel
        }
        if (mDataStore.dateLabel != null) {
            date_helper.text = mDataStore.dateLabel
        }
        if (mDataStore.cvvLabel != null) {
            cvv_input.hint = mDataStore.cvvLabel
        }
    }

    /**
     * Used to restore state on orientation changes
     *
     *
     * The method will repopulate all the card input fields with the latest state they were in
     * if the device orientation changes, and therefore avoiding the text inputs to be cleared.
     */
    private fun repopulateField() {
        //Repopulate billing spinner
        updateBillingSpinner()
    }

    /**
     * Used to indicate the validity of the date
     *
     *
     * The method will check if the values from the [MonthInput] and [YearInput] are
     * not representing a date in the past.
     *
     * @return boolean abut form validity of the date
     */
    private fun checkFullDate(): Boolean {
        // Check is the state contain the date and if it is check if the current selected
        // values are valid. Display error if applicable.
        month_input.showError(!inMemoryStore.cardDate.isMonthValid())
        year_input.showError(!inMemoryStore.cardDate.isYearValid())
        return inMemoryStore.cardDate.isDateValid()
    }

    /**
     * Used to clear/reset the billing details spinner
     *
     *
     * The method will be used to clear/reset the billing details spinner in case the user
     * has decide to clear their details from the [BillingDetailsView]
     */
    fun clearBillingSpinner() {
        val billingElement = ArrayList<String>()

        // Set the default value fo the spinner
        billingElement.add(resources.getString(R.string.select_billing_details))
        billingElement.add(resources.getString(R.string.billing_details_add))

        val dataAdapter = ArrayAdapter(
            mContext,
            android.R.layout.simple_spinner_item, billingElement
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        go_to_billing.adapter = dataAdapter
        go_to_billing.setSelection(0)
    }

    /**
     * Used to populate the billing spinner with the user billing details
     *
     *
     * The method will be called when the user has successfully saved their billing details and
     * to visually confirm that, the spinner is populated with the details and the default ADD
     * button is replaced by a EDIT button
     */
    fun updateBillingSpinner() {

        val address = mDataStore.customerAddress1 +
                ", " + mDataStore.customerAddress2 +
                ", " + mDataStore.customerCity +
                ", " + mDataStore.customerState

        // Avoid updates for there are no values set
        if (address.length > 6) {
            val billingElement = ArrayList<String>()

            billingElement.add(address)
            billingElement.add("Edit")

            val dataAdapter = ArrayAdapter(
                mContext,
                android.R.layout.simple_spinner_item, billingElement
            )
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            go_to_billing.adapter = dataAdapter
            go_to_billing.setSelection(0)
        }
    }

    /**
     * Used to clear the text and state of the fields
     *
     *
     */
    fun resetFields() {
        if (mDataStore.defaultBillingDetails != null) {
            updateBillingSpinner()
        } else {
            clearBillingSpinner()
        }
        cvv_input.reset()
        year_input.reset()
        month_input.reset()
        card_input.clear()
        card_input_layout.error = null
        card_input_layout.isErrorEnabled = false
    }

    /**
     * Used dynamically populate the accepted cards view is the option is used
     */
    private fun setAcceptedCards() {

        val allCards = if (mDataStore.acceptedCards != null)
            mDataStore.acceptedCards
        else
            Arrays.asList<CardUtils.Cards>(*CardUtils.Cards.values()).toTypedArray()

        val size =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35f, resources.displayMetrics)
                .toInt()
        val margin =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3f, resources.displayMetrics)
                .toInt()

        for (card in allCards) {
            val image = ImageView(mContext)
            image.layoutParams = android.view.ViewGroup.LayoutParams(size, size)
            image.setImageResource(card.resourceId)
            val marginParams = ViewGroup.MarginLayoutParams(image.layoutParams)
            marginParams.setMargins(0, 0, margin, 0)

            // Adds the view to the layout
            mAcceptedCardsView?.addView(image)
        }

    }

    /**
     * This method used to generate a [CardTokenisationRequest] with the details
     * completed by the user in the payment from
     * displayed in the payment form.
     *
     * @return CardTokenisationRequest
     */
    private fun generateRequest(): CardTokenisationRequest {
        val request: CardTokenisationRequest
        if (mDataStore.isBillingCompleted) {
            request = CardTokenisationRequest(
                sanitizeEntry(mDataStore.cardNumber),
                mDataStore.customerName,
                DateFormatter().formatMonth(inMemoryStore.cardDate.month.monthInteger),
                inMemoryStore.cardDate.year.toString(),
                inMemoryStore.cvv.value,
                BillingModel(
                    mDataStore.customerAddress1,
                    mDataStore.customerAddress2,
                    mDataStore.customerZipcode,
                    mDataStore.customerCountry,
                    mDataStore.customerCity,
                    mDataStore.customerState,
                    PhoneModel(
                        mDataStore.customerPhonePrefix,
                        mDataStore.customerPhone
                    )
                )
            )
        } else {
            request = CardTokenisationRequest(
                sanitizeEntry(mDataStore.cardNumber),
                mDataStore.customerName,
                DateFormatter().formatMonth(inMemoryStore.cardDate.month.monthInteger),
                inMemoryStore.cardDate.year.value.toString(),
                inMemoryStore.cvv.value, null
            )
        }

        return request
    }

    /**
     * Returns a String without any spaces
     *
     *
     * This method used to take a card number input String and return a
     * String that simply removed all whitespace, keeping only digits.
     *
     * @param entry the String value of a card number
     */
    private fun sanitizeEntry(entry: String): String {
        return entry.replace("\\D".toRegex(), "")
    }


    /**
     * Used to set the callback listener for when the form is submitted
     */
    fun setDetailsCompletedListener(listener: CardDetailsView.DetailsCompleted) {
        mDetailsCompletedListener = listener
    }

    /**
     * Used to set the callback listener for when the billing details page is requested
     */
    fun setGoToBillingListener(listener: GoToBillingListener) {
        mGotoBillingListener = listener
    }
}
