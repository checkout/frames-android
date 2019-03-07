package com.checkout.sdk.monthinput

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.architecture.PresenterStore
import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.utils.DateFormatter

/**
 * A custom Spinner with handling of card expiration month input
 */
class MonthInput @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null
) :
    android.support.v7.widget.AppCompatSpinner(mContext, attrs),
    MvpView<MonthInputUiState> {

    private var monthInputListener: MonthListener? = null
    private lateinit var presenter: MonthInputPresenter

    interface MonthListener {
        fun onMonthInputFinish(month: String)
    }

    /**
     * The UI initialisation
     *
     *
     * Used to initialise element as well as setting up appropriate listeners
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        presenter = PresenterStore.getOrCreate(
            MonthInputPresenter::class.java,
            {
                MonthInputPresenter(DateFormatter())
            })
        presenter.start(this)

        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val monthSelectedUseCase = MonthSelectedUseCase(position, InMemoryStore.Factory.get())
                presenter.monthSelected(monthSelectedUseCase)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                performClick()
                val imm =
                    mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.stop()
    }

    /**
     * Populate the spinner with all the month of the year
     */
    override fun onStateUpdated(uiState: MonthInputUiState) {
        if (adapter == null) {
            val dataAdapter = ArrayAdapter(
                mContext,
                android.R.layout.simple_spinner_item,
                uiState.months
            )
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter = dataAdapter
        }
        if (selectedItemPosition != uiState.position) {
            setSelection(uiState.position)
        }
    }

    /**
     * Resets the values for the MonthInput view
     */
    fun reset() {
        val monthResetUseCase = MonthResetUseCase(InMemoryStore.Factory.get())
        presenter.reset(monthResetUseCase)
    }
}
