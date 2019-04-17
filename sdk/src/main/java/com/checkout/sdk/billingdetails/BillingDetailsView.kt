package com.checkout.sdk.billingdetails

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.checkout.sdk.R
import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.architecture.PresenterStore
import com.checkout.sdk.store.InMemoryStore
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

    private val inMemoryStore: InMemoryStore = InMemoryStore.Factory.get()
    private val countriesManager: CountriesManager = CountriesManager()
    private var listener: BillingDetailsView.Listener? = null
    private lateinit var presenter: BillingDetailsPresenter

    /**
     * The callback used to indicate when the billing details are finished
     *
     * This callback is invoked when the user presses the back button;
     * or when they click the done button with valid values for every field
     */
    interface Listener {
        fun onBillingFinished()

    }

    init {
        inflate(this.context, R.layout.billing_details, this)
        orientation = VERTICAL
        isFocusableInTouchMode = true
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val positionZeroString = context.getString(R.string.placeholder_country)
        presenter = PresenterStore.getOrCreate(
            BillingDetailsPresenter::class.java,
            { BillingDetailsPresenter(createInitialUiState(positionZeroString)) })
        presenter.start(this)
        phone_input.listenForRepositoryChange()

        my_toolbar.setNavigationOnClickListener {
            listener?.onBillingFinished()
        }
        country_input.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val countrySelectedUseCaseBuilder =
                    CountrySelectedUseCase.Builder(countriesManager, inMemoryStore, position)
                presenter.countrySelected(countrySelectedUseCaseBuilder)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Nothing
            }
        }

        clear_button.setOnClickListener {
            resetFields()
        }

        done_button.setOnClickListener {
            val doneButtonClickedUseCase =
                DoneButtonClickedUseCase(BillingDetailsValidator(inMemoryStore))
            presenter.doneButtonClicked(doneButtonClickedUseCase)
        }
    }

    private fun createInitialUiState(positionZeroString: String): BillingDetailsUiState {
        return BillingDetailsUiState.create(
            CountriesManager(),
            inMemoryStore,
            positionZeroString
        )
    }

    override fun onStateUpdated(uiState: BillingDetailsUiState) {
        if (!uiState.countries.isEmpty() && country_input.adapter == null) {
            val adapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                uiState.countries
            )
            country_input.adapter = adapter
        }
        if (country_input.selectedItemPosition != uiState.position) {
            country_input.setSelection(uiState.position)
        }
        uiState.billingDetailsValidity?.let {
            updateFieldValidity(it)
            if (it.areDetailsValid()) {
                listener?.onBillingFinished()
            }
        }
    }

    private fun updateFieldValidity(validity: BillingDetailsValidity) {
        name_input.showError(!validity.nameValid)
        address_one_input.showError(!validity.addressOneValid)
        address_two_input.showError(!validity.addressTwoValid)
        city_input.showError(!validity.cityValid)
        state_input.showError(!validity.stateValid)
        zipcode_input.showError(!validity.zipcodeValid)
        showCountryError(validity)
        phone_input.showError(!validity.phoneValid)
    }

    private fun showCountryError(validity: BillingDetailsValidity) {
        val errorView = country_input.selectedView as? TextView
        errorView?.let {
            if (validity.countryValid) {
                it.error = null
            } else {
                it.error = resources.getString(R.string.error_country)
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.stop()
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
        zipcode_input.reset()
        country_input.setSelection(0)
        phone_input.reset()
    }

    /**
     * Used to set the callback listener for when the card details page is requested
     */
    fun setGoToCardDetailsListener(listener: BillingDetailsView.Listener) {
        this.listener = listener
    }
}
