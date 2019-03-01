package com.checkout.android_sdk.Input

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.checkout.android_sdk.Store.DataStore
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

/**
 * A custom Spinner with handling of card expiration month input
 */
class MonthInput @JvmOverloads constructor(private val mContext: Context, attrs: AttributeSet? = null) :
    android.support.v7.widget.AppCompatSpinner(mContext, attrs) {

    private var mMonthInputListener: MonthInput.MonthListener? = null
    private val mDatastore = DataStore.getInstance()
    private val monthElements = DateFormatSymbols().shortMonths

    interface MonthListener {
        fun onMonthInputFinish(month: String)
    }

    /**
     * The UI initialisation
     *
     *
     * Used to initialise element as well as setting up appropriate listeners
     */
    init {
        populateSpinner()

        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val numberString = formatMonth(position + 1)
                mDatastore.cardMonth = numberString

                mMonthInputListener?.let {
                    it.onMonthInputFinish(numberString)
                }
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

    /**
     * Populate the spinner with all the month of the year
     */
    private fun populateSpinner() {
        for (i in 0 until monthElements.size) {
            monthElements[i] = monthElements[i].toUpperCase() + " - " + formatMonth(i + 1)
        }

        val dataAdapter = ArrayAdapter(
            mContext,
            android.R.layout.simple_spinner_item, monthElements
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter = dataAdapter
    }

    /**
     * Turn the month integer into a formatted String: 1 -> 01 etc
     */
    private fun formatMonth(monthInteger: Int): String {
        val monthParse = SimpleDateFormat("MM", Locale.getDefault())
        val monthDisplay = SimpleDateFormat("MM", Locale.getDefault())
        return monthDisplay.format(monthParse.parse(monthInteger.toString()))
    }

    /**
     * Used to set the callback listener for when the month input is completed
     */
    fun setMonthListener(listener: MonthInput.MonthListener) {
        this.mMonthInputListener = listener
    }
}
