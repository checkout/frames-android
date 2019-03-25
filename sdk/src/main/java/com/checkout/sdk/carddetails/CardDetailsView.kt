package com.checkout.sdk.carddetails

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import com.checkout.sdk.R
import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.architecture.PresenterStore
import com.checkout.sdk.core.CardDetailsValidator
import com.checkout.sdk.core.CardDetailsValidity
import com.checkout.sdk.input.BillingInput
import com.checkout.sdk.paymentform.PaymentForm
import com.checkout.sdk.store.DataStore
import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.utils.CardUtils
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
) : LinearLayout(mContext, attrs), MvpView<CardDetailsUiState> {

    private val inMemoryStore = InMemoryStore.Factory.get()
    lateinit var presenter: CardDetailsPresenter
    private lateinit var validPayRequestListener: PaymentForm.ValidPayRequestListener

    fun showProgress(inProgress: Boolean) {
        presenter.showProgress(inProgress)
    }

    override fun onStateUpdated(uiState: CardDetailsUiState) {
        if (uiState.hideKeyboard) {
            hideKeyboard()
        }
        val visibility = if (uiState.inProgress) View.VISIBLE else View.INVISIBLE
        Log.e("JOHN", "Visibility: $visibility")
        progress_bar.visibility = visibility

        uiState.cardDetailsValidity?.let {
            updateFieldValidity(it)
            if (it.areDetailsValid()) {
                validPayRequestListener.onValidPayRequest()
            }
        }
    }

    private fun updateFieldValidity(validity: CardDetailsValidity) {
        card_input.showError(!validity.cardNumberValid)
        cvv_input.showError(!validity.cvvValid)
        month_input.showError(!validity.monthValid)
        year_input.showError(!validity.yearValid)
    }

    /**
     * The callback is used to trigger the focus change to the billing page
     */
    private val mBillingInputListener = BillingInput.BillingListener {
        mGotoBillingListener?.onGoToBillingPressed()
    }

    private var mDataStore: DataStore = DataStore.getInstance()
    private var mGotoBillingListener: GoToBillingListener? = null


    private var mAcceptedCardsView: LinearLayout? = null

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

    init {
        inflate(mContext, R.layout.card_details, this)
        orientation = VERTICAL
    }

    /**
     * The UI initialisation
     *
     *
     * Used to initialise element and pass callbacks as well as setting up appropriate listeners
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        presenter = PresenterStore.getOrCreate(
            CardDetailsPresenter::class.java,
            { CardDetailsPresenter() })
        presenter.start(this)

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

        // TODO: pay_button should probably be in PaymentForm's view
        pay_button.setOnClickListener {
            val playButtonClickedUseCase = PayButtonClickedUseCase(CardDetailsValidator(inMemoryStore))
            presenter.payButtonClicked(playButtonClickedUseCase)
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
            card_input.hint = mDataStore.cardLabel
        }
        if (mDataStore.dateLabel != null) {
            date_helper.text = mDataStore.dateLabel
        }
        if (mDataStore.cvvLabel != null) {
            cvv_input.hint = mDataStore.cvvLabel
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.stop()
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
        card_input.reset()
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
     * Used to set the callback listener for when the form is submitted
     */
    fun setValidCardDetailsListener(listener: PaymentForm.ValidPayRequestListener) {
        validPayRequestListener = listener
    }

    /**
     * Used to set the callback listener for when the billing details page is requested
     */
    fun setGoToBillingListener(listener: GoToBillingListener) {
        mGotoBillingListener = listener
    }

    private fun hideKeyboard() {
        try {
            val imm =
                mContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
