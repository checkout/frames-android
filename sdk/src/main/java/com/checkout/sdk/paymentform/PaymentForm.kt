package com.checkout.sdk.paymentform

import android.content.Context
import android.net.Uri
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.checkout.sdk.CheckoutClient
import com.checkout.sdk.R
import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.architecture.PresenterStore
import com.checkout.sdk.carddetails.CardDetailsView
import com.checkout.sdk.core.RequestGenerator
import com.checkout.sdk.models.BillingModel
import com.checkout.sdk.store.DataStore
import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.utils.*
import com.checkout.sdk.view.BillingDetailsView
import java.util.*

/**
 * Contains helper methods dealing with the tokenisation or payment from customisation
 *
 *
 * Most of the methods that include interaction with the Checkout.com API rely on
 * callbacks to communicate outcomes. Please make sure you set the key/environment
 * and appropriate  callbacks to a ensure successful interaction
 */
class PaymentForm @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null
) : FrameLayout(mContext, attrs), MvpView<PaymentFormUiState> {

    private val inMemoryStore = InMemoryStore.Factory.get()
    private lateinit var checkoutClient: CheckoutClient
    private lateinit var presenter: PaymentFormPresenter

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter = PresenterStore.getOrCreate(
            PaymentFormPresenter::class.java,
            { PaymentFormPresenter() })
        presenter.start(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.stop()
    }

    fun initialize(checkoutClient: CheckoutClient) {
        this.checkoutClient = checkoutClient
    }

    override fun onStateUpdated(uiState: PaymentFormUiState) {
        // TODO: We should pull the spinner in as a part of the Payment Form
    }

    private var validCardDetailsListener: ValidCardDetailsListener =
        object : ValidCardDetailsListener {
            override fun onValidCardDetails() {
                val getTokenUseCase = GetTokenUseCase(
                    RequestGenerator(inMemoryStore, mDataStore, DateFormatter()),
                    checkoutClient
                )
                presenter.getToken(getTokenUseCase)
            }
        }

    interface ValidCardDetailsListener {
        fun onValidCardDetails()
    }

    /**
     * This is a callback used to go back to the card details view from the billing page
     * and based on the action used decide is the billing spinner will be updated
     */
    private val mBillingListener = object : BillingDetailsView.Listener {
        override fun onBillingCompleted() {
            customAdapter!!.updateBillingSpinner()
            mPager!!.currentItem = CARD_DETAILS_PAGE_INDEX
        }

        override fun onBillingCanceled() {
            customAdapter!!.clearBillingSpinner()
            mPager!!.currentItem = CARD_DETAILS_PAGE_INDEX
        }
    }

    /**
     * This is a callback used to navigate to the billing details page
     */
    private val mCardListener = object : CardDetailsView.GoToBillingListener {
        override fun onGoToBillingPressed() {
            mPager!!.currentItem = BILLING_DETAILS_PAGE_INDEX
        }
    }
    var m3DSecureListener: On3DSFinished? = null

    private var customAdapter: CustomAdapter? = null
    private var mPager: ViewPager? = null
    private val mDataStore = DataStore.getInstance()

    /**
     * This is interface used as a callback for when the 3D secure functionality is used
     */
    interface On3DSFinished {
        fun onSuccess(token: String)

        fun onError(errorMessage: String)
    }

    /**
     * This method is used to initialise the UI of the module
     */
    init {
        // Set up the layout
        View.inflate(mContext, R.layout.payment_form, this)

        mPager = findViewById(R.id.view_pager)
        // Use a custom adapter for the viewpager
        customAdapter = CustomAdapter(mContext)
        // Set up the callbacks
        customAdapter!!.setCardDetailsListener(mCardListener)
        customAdapter!!.setValidCardDetailsListener(validCardDetailsListener)
        customAdapter!!.setBillingListener(mBillingListener)
        mPager!!.adapter = customAdapter
        mPager!!.isEnabled = false
    }

    /**
     * This method is used set the accepted card schemes
     *
     * @param cards array of accepted cards
     */
    fun setAcceptedCard(cards: Array<CardUtils.Cards>): PaymentForm {
        mDataStore.acceptedCards = cards
        return this
    }

    /**
     * This method is used to handle 3D Secure URLs.
     *
     *
     * It wil programmatically generate a WebView and listen for when the url changes
     * in either the success url or the fail url.
     *
     * @param url        the 3D Secure url
     * @param successUrl the Redirection url set up in the Checkout.com HUB
     * @param failsUrl   the Redirection Fail url set up in the Checkout.com HUB
     */
    fun handle3DS(url: String, successUrl: String, failsUrl: String) {
        if (mPager != null) {
            mPager!!.visibility = View.GONE // dismiss the card form UI
        }
        val web = WebView(mContext)
        web.loadUrl(url)
        web.settings.javaScriptEnabled = true
        web.webViewClient = object : WebViewClient() {
            // Listen for when teh URL changes and match t with either the success of fail url
            override fun onPageFinished(view: WebView, url: String) {
                if (url.contains(successUrl)) {
                    val uri = Uri.parse(url)
                    val paymentToken = uri.getQueryParameter("cko-payment-token")
                    m3DSecureListener?.onSuccess(paymentToken)
                } else if (url.contains(failsUrl)) {
                    val uri = Uri.parse(url)
                    val paymentToken = uri.getQueryParameter("cko-payment-token")
                    m3DSecureListener?.onError(paymentToken)
                }
            }
        }
        // Make WebView fill the layout
        web.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        addView(web)
    }

    /**
     * This method used to decide if the billing details option will be
     * displayed in the payment form.
     *
     * @param include boolean showing if the billing should be used
     */
    fun includeBilling(include: Boolean) {
        if (!include) {
            mDataStore.setShowBilling(false)
        } else {
            mDataStore.setShowBilling(true)
        }
    }

    /**
     * This method used to set a default country for the country
     *
     * @param country Locale representing the default country for the Spinner
     */
    fun setDefaultBillingCountry(country: Locale): PaymentForm {
        mDataStore.customerCountry = country.country
        mDataStore.defaultCountry = country
        mDataStore.customerPhonePrefix = PhoneUtils.getPrefix(country.country)
        return this
    }

    /**
     * This method used to set a custom label for the accepted cards
     *
     * @param accepted String representing the value for the Label
     */
    fun setAcceptedCardsLabel(accepted: String): PaymentForm {
        mDataStore.acceptedLabel = accepted
        return this
    }

    /**
     * This method used to set a custom label for the CardInput
     *
     * @param card String representing the value for the Label
     */
    fun setCardLabel(card: String): PaymentForm {
        mDataStore.cardLabel = card
        return this
    }

    /**
     * This method used to set a custom label for the DateInput
     *
     * @param date String representing the value for the Label
     */
    fun setDateLabel(date: String): PaymentForm {
        mDataStore.dateLabel = date
        return this
    }

    /**
     * This method used to set a custom label for the CvvInput
     *
     * @param cvv String representing the value for the Label
     */
    fun setCvvLabel(cvv: String): PaymentForm {
        mDataStore.cvvLabel = cvv
        return this
    }

    /**
     * This method used to set a custom label for the CardholderInput
     *
     * @param label String representing the value for the Label
     */
    fun setCardHolderLabel(label: String): PaymentForm {
        mDataStore.cardHolderLabel = label
        return this
    }

    /**
     * This method used to set a custom label for the AddressLine 1 Input
     *
     * @param label String representing the value for the Label
     */
    fun setAddress1Label(label: String): PaymentForm {
        mDataStore.addressLine1Label = label
        return this
    }

    /**
     * This method used to set a custom label for the AddressLine 2 Input
     *
     * @param label String representing the value for the Label
     */
    fun setAddress2Label(label: String): PaymentForm {
        mDataStore.addressLine2Label = label
        return this
    }

    /**
     * This method used to set a custom label for the StateInput
     *
     * @param label String representing the value for the Label
     */
    fun setTownLabel(label: String): PaymentForm {
        mDataStore.townLabel = label
        return this
    }

    /**
     * This method used to set a custom label for the StateInput
     *
     * @param label String representing the value for the Label
     */
    fun setStateLabel(label: String): PaymentForm {
        mDataStore.stateLabel = label
        return this
    }

    /**
     * This method used to set a custom label for the PostcodeInput
     *
     * @param label String representing the value for the Label
     */
    fun setPostcodeLabel(label: String): PaymentForm {
        mDataStore.postCodeLabel = label
        return this
    }

    /**
     * This method used to set a custom label for the PhoneInput
     *
     * @param label String representing the value for the Label
     */
    fun setPhoneLabel(label: String): PaymentForm {
        mDataStore.phoneLabel = label
        return this
    }

    /**
     * This method used to set a custom text for the Pay button
     *
     * @param text String representing the text for the Button
     */
    fun setPayButtonText(text: String): PaymentForm {
        mDataStore.payButtonText = text
        return this
    }

    /**
     * This method used to set a custom text for the Done button
     *
     * @param text String representing the text for the Button
     */
    fun setDoneButtonText(text: String): PaymentForm {
        mDataStore.doneButtonText = text
        return this
    }

    /**
     * This method used to set a custom text for the Clear button
     *
     * @param text String representing the text for the Button
     */
    fun setClearButtonText(text: String): PaymentForm {
        mDataStore.clearButtonText = text
        return this
    }

    /**
     * This method used to set a custom LayoutParameters for the Pay button
     *
     * @param layout LayoutParameters representing the style for the Button
     */
    fun setPayButtonLayout(layout: LinearLayout.LayoutParams): PaymentForm {
        mDataStore.payButtonLayout = layout
        return this
    }

    /**
     * This method used to set a custom LayoutParameters for the Done button
     *
     * @param layout LayoutParameters representing the style for the Button
     */
    fun setDoneButtonLayout(layout: LinearLayout.LayoutParams): PaymentForm {
        mDataStore.doneButtonLayout = layout
        return this
    }

    /**
     * This method used to set a custom LayoutParameters for the Clear button
     *
     * @param layout LayoutParameters representing the style for the Button
     */
    fun setClearButtonLayout(layout: LinearLayout.LayoutParams): PaymentForm {
        mDataStore.clearButtonLayout = layout
        return this
    }

    /**
     * This method used to inject address details if they have already been collected
     *
     * @param billing BillingModel representing the value for the billing details
     */
    fun injectBilling(billing: BillingModel): PaymentForm {
        mDataStore.isBillingCompleted = true
        mDataStore.lastBillingValidState = billing
        mDataStore.defaultBillingDetails = billing
        mDataStore.customerAddress1 = billing.addressLine1
        mDataStore.customerAddress2 = billing.addressLine2
        mDataStore.customerZipcode = billing.postcode
        mDataStore.customerCountry = billing.country
        mDataStore.customerCity = billing.city
        mDataStore.customerState = billing.state
        mDataStore.customerPhone = billing.phone.number
        mDataStore.customerPhonePrefix = billing.phone.countryCode
        return this
    }

    fun setEnvironment(env: Environment): PaymentForm {
        mDataStore.environment = env
        return this
    }

    fun setKey(key: String): PaymentForm {
        mDataStore.key = key
        return this
    }

    /**
     * This method used to inject the cardholder name if it has already been collected
     *
     * @param name String representing the value for the cardholder name
     */
    fun injectCardHolderName(name: String): PaymentForm {
        mDataStore.customerName = name
        mDataStore.defaultCustomerName = name
        mDataStore.lastCustomerNameState = name
        return this
    }

    /**
     * This method used to clear the state and fields of the Payment Form
     */
    fun clearForm() {
        mDataStore.cleanState()
        if (mDataStore != null && mDataStore.defaultBillingDetails != null) {
            mDataStore.isBillingCompleted = true
            mDataStore.lastBillingValidState = mDataStore.defaultBillingDetails
            mDataStore.customerAddress1 = mDataStore.defaultBillingDetails.addressLine1
            mDataStore.customerAddress2 = mDataStore.defaultBillingDetails.addressLine2
            mDataStore.customerZipcode = mDataStore.defaultBillingDetails.postcode
            mDataStore.customerCountry = mDataStore.defaultBillingDetails.country
            mDataStore.customerCity = mDataStore.defaultBillingDetails.city
            mDataStore.customerState = mDataStore.defaultBillingDetails.state
            mDataStore.customerPhone = mDataStore.defaultBillingDetails.phone.number
            mDataStore.customerPhonePrefix = mDataStore.defaultBillingDetails.phone.countryCode
        }
        if (mDataStore != null && mDataStore.defaultCustomerName != null) {
            mDataStore.customerName = mDataStore.defaultCustomerName
        } else {
            mDataStore.lastCustomerNameState = null
        }
        if (mDataStore != null && mDataStore.defaultCountry != null) {
            mDataStore.defaultCountry = mDataStore.defaultCountry
        }
        customAdapter!!.clearFields()
        if (mDataStore != null && mDataStore.defaultBillingDetails != null) {
            mDataStore.isBillingCompleted = true
            mDataStore.lastBillingValidState = mDataStore.defaultBillingDetails
        } else {
            mDataStore.lastBillingValidState = null
        }
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
     * This method used to set a callback for when the 3D Secure handling.
     */
    fun set3DSListener(listener: On3DSFinished): PaymentForm {
        this.m3DSecureListener = listener
        return this
    }

    companion object {

        // Indexes for the pages
        private const val CARD_DETAILS_PAGE_INDEX = 0
        private const val BILLING_DETAILS_PAGE_INDEX = 1
    }

}
