package com.checkout.android_sdk.Input

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.checkout.android_sdk.Architecture.PresenterStore
import com.checkout.android_sdk.Presenter.MonthInputPresenter
import com.checkout.android_sdk.Store.DataStore
import com.checkout.android_sdk.Utils.DateFormatter

/**
 * A custom Spinner with handling of card expiration month input
 */
class MonthInput @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null
) :
    android.support.v7.widget.AppCompatSpinner(mContext, attrs),
    MonthInputPresenter.MonthInputView {

    private var monthInputListener: MonthInput.MonthListener? = null
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
            { MonthInputPresenter(DateFormatter(), DataStore.getInstance()) })
        presenter.start(this)

        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                presenter.monthSelected(position)
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
    override fun onStateUpdated(uiState: MonthInputPresenter.MonthInputUiState) {
        if (adapter == null) {
            val dataAdapter = ArrayAdapter(
                mContext,
                android.R.layout.simple_spinner_item,
                uiState.months
            )
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter = dataAdapter
        }
        if (uiState.finished) {
            monthInputListener?.onMonthInputFinish(uiState.numberString)
        }
        if (uiState.position != -1) {
            setSelection(uiState.position)
        }
    }

    /**
     * Used to set the callback listener for when the month input is completed
     */
    fun setMonthListener(listener: MonthInput.MonthListener) {
        this.monthInputListener = listener
    }
}
