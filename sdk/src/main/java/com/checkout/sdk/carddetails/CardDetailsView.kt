package com.checkout.sdk.carddetails

import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import com.checkout.sdk.FormCustomizer
import com.checkout.sdk.R
import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.architecture.PresenterStore
import com.checkout.sdk.billingdetails.BillingDetailsValidator
import com.checkout.sdk.core.Card
import com.checkout.sdk.core.CardDetailsValidator
import com.checkout.sdk.core.CardDetailsValidity
import com.checkout.sdk.paymentform.PaymentForm
import com.checkout.sdk.store.InMemoryStore
import com.google.android.flexbox.FlexboxLayout
import kotlinx.android.synthetic.main.card_details.view.*

/**
 * The controller of the card details view page
 *
 *
 * This class handles interaction with the custom inputs in the card details form.
 * The state of the view is handled here, so are action like focus changes, full form
 * validation, listeners, persistence over orientation.
 */
class CardDetailsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs), MvpView<CardDetailsUiState> {

    private val inMemoryStore = InMemoryStore.Factory.get()
    lateinit var presenter: CardDetailsPresenter
    private var validPayRequestListener: PaymentForm.ValidPayRequestListener? = null
    private var mGotoBillingListener: GoToBillingListener? = null

    private val resetBillingSpinnerUseCase = UpdateBillingSpinnerUseCase(
        inMemoryStore,
        BillingDetailsValidator(inMemoryStore),
        context.getString(R.string.select_billing_details),
        context.getString(R.string.billing_details_add),
        context.getString(R.string.edit_billing_details),
        context.getString(R.string.address_format)
    )
    private val playButtonClickedUseCase =
        PayButtonClickedUseCase(CardDetailsValidator(inMemoryStore))

    /**
     * The callback used to indicate the view needs to moved to the billing details page
     *
     *
     * When the user selects the option to add billing details this callback will be used
     * to communicate to the parent the focus change is requested
     */
    interface GoToBillingListener {
        fun onGoToBilling()
    }

    init {
        inflate(context, R.layout.card_details, this)
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

        presenter = PresenterStore.getOrCreateDefault(CardDetailsPresenter::class.java)
        presenter.start(this)

        go_to_billing.setBillingListener(mGotoBillingListener)

        pay_button.setOnClickListener {
            presenter.payButtonClicked(playButtonClickedUseCase)
        }

        initializeAcceptedCards()
        updateBillingSpinner()
    }

    /**
     * Display a progress bar to show that Payment is in progress
     */
    fun showProgress(inProgress: Boolean) {
        presenter.showProgress(inProgress)
    }

    override fun onStateUpdated(uiState: CardDetailsUiState) {
        if (uiState.hideKeyboard) {
            hideKeyboard()
        }
        val visibility = if (uiState.inProgress) View.VISIBLE else View.INVISIBLE
        progress_bar.visibility = visibility

        uiState.cardDetailsValidity?.let {
            updateFieldValidity(it)
            if (it.areDetailsValid()) {
                validPayRequestListener?.onValidPayRequest()
            }
        }
        uiState.acceptedCards?.let { displayAcceptedCards(it) }
        uiState.spinnerStrings?.let { populateBillingSpinner(it) }
    }

    private fun displayAcceptedCards(cards: List<Card>) {
        card_icons_flexbox.removeAllViews()
        for (card in cards) {
            val cardIconView = inflate(context, R.layout.view_credit_card_icon, null) as ImageView
            val cardDrawable = ContextCompat.getDrawable(context, card.resourceId)
            cardIconView.setImageDrawable(cardDrawable)

            val cardIconSize = context.resources.getDimensionPixelSize(R.dimen.cards_icon_size)
            val layoutParams = FlexboxLayout.LayoutParams(cardIconSize, cardIconSize)
            val cardRightMargin = context.resources.getDimensionPixelSize(R.dimen.cards_icon_margin)
            layoutParams.setMargins(0, 0, cardRightMargin, 0)
            cardIconView.layoutParams = layoutParams
            card_icons_flexbox.addView(cardIconView)
        }
    }

    private fun updateFieldValidity(validity: CardDetailsValidity) {
        card_input.showError(!validity.cardNumberValid)
        cvv_input.showError(!validity.cvvValid)
        month_input.showError(!validity.monthValid)
        year_input.showError(!validity.yearValid)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.stop()
    }

    /**
     * Used to clear the text and state of the fields
     */
    fun resetFields() {
        updateBillingSpinner()
        cvv_input.reset()
        year_input.reset()
        month_input.reset()
        card_input.reset()
    }

    /**
     * Used to update the billing spinner based on values added in the BillingDetailsView
     */
    fun updateBillingSpinner() {
        presenter.updateBillingSpinner(resetBillingSpinnerUseCase)
    }

    /**
     * Used dynamically populate the accepted cards view is the option is used
     */
    private fun initializeAcceptedCards() {
        val initializeAcceptedCardsUseCase = InitializeAcceptedCardsUseCase(FormCustomizer.Holder.get().getFormCustomizer())
        presenter.initializeAcceptedCards(initializeAcceptedCardsUseCase)
    }

    private fun populateBillingSpinner(elements: List<String>) {
        val dataAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item,
            elements
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        go_to_billing.adapter = dataAdapter
        go_to_billing.setSelection(0)
    }

    /**
     * Used to set the callback listener for when the form is submitted
     */
    fun setValidPayRequestListener(listener: PaymentForm.ValidPayRequestListener) {
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
                context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
