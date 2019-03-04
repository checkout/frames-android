package com.checkout.android_sdk.Input

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.checkout.android_sdk.Architecture.PresenterStore
import com.checkout.android_sdk.Presenter.YearInputPresenter
import com.checkout.android_sdk.Store.DataStore

/**
 * A custom Spinner with handling of card expiration year input
 */
class YearInput(internal var mContext: Context, attrs: AttributeSet? = null) :
    android.support.v7.widget.AppCompatSpinner(mContext, attrs), YearInputPresenter.YearInputView {

    private var mYearInputListener: YearInput.YearListener? = null
    private lateinit var presenter: YearInputPresenter

    override fun onStateUpdated(uiState: YearInputPresenter.YearInputUiState) {
        if (adapter == null) {
            val dataAdapter = ArrayAdapter(
                mContext,
                android.R.layout.simple_spinner_item, uiState.years
            )
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter = dataAdapter
        }
        if (uiState.position != -1) {
            setSelection(uiState.position)
        }
    }

    interface YearListener {
        fun onYearInputFinish(month: String)
    }

    /**
     * The UI initialisation
     * <p>
     * Used to initialise element as well as setting up appropriate listeners
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        // Create/get and start the presenter
        presenter = PresenterStore.getOrCreate(
            YearInputPresenter::class.java,
            { YearInputPresenter(DataStore.getInstance()) })
        presenter.start(this)

        onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                performClick()
                val imm =
                    mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }

        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                presenter.yearSelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        // Remove extra padding left
        setPadding(0, this.paddingTop, this.paddingRight, this.paddingBottom)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.stop()
    }

    /**
     * Used to set the callback listener for when the year input is completed
     */
    fun setYearListener(listener: YearInput.YearListener) {
        this.mYearInputListener = listener
    }
}
