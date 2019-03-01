package com.checkout.android_sdk.Input

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.checkout.android_sdk.Store.DataStore
import java.util.*

/**
 * A custom Spinner with handling of card expiration month input
 */
class MonthInput @JvmOverloads constructor(private val mContext: Context, attrs: AttributeSet? = null) :
    android.support.v7.widget.AppCompatSpinner(mContext, attrs) {

    private var mMonthInputListener: MonthInput.MonthListener? = null
    private val mDatastore = DataStore.getInstance()

    interface MonthListener {
        fun onMonthInputFinish(month: String)
    }

    // enum with month is different formats
    enum class Months constructor(
        val monthName: String,
        val number: Int,
        val numberString: String
    ) {
        JANUARY("JAN", 1, "01"),
        FEBRUARY("FEB", 2, "02"),
        MARCH("MAR", 3, "03"),
        APRIL("APR", 4, "04"),
        MAY("MAY", 5, "05"),
        JUNE("JUN", 6, "06"),
        JULY("JUL", 7, "07"),
        AUGUST("AUG", 8, "08"),
        SEPTEMBER("SEP", 9, "09"),
        OCTOBER("OCT", 10, "10"),
        NOVEMBER("NOV", 11, "11"),
        DECEMBER("DEC", 12, "12")

    }

    /**
     * The UI initialisation
     *
     *
     * Used to initialise element as well as setting up appropriate listeners
     */
    init {
        // Options needed for focus context switching
        isFocusable = true
        isFocusableInTouchMode = true

        populateSpinner()

        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val months = MonthInput.Months.values()

                mDatastore.cardMonth = months[position].numberString

                for (i in 0..11) {
                    if (mMonthInputListener != null && months[i].number - 1 == position) {
                        mMonthInputListener!!.onMonthInputFinish(months[i].numberString)
                        break
                    }
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
                imm?.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }

        // Remove extra padding left
        setPadding(0, this.paddingTop, this.paddingRight, this.paddingBottom)

    }

    /**
     * Populate the spinner with all the month of the year
     */
    private fun populateSpinner() {
        val months = MonthInput.Months.values()

        val monthElements = ArrayList<String>()

        for (i in 0..11) {
            monthElements.add(months[i].monthName + " - " + months[i].numberString)
        }

        val dataAdapter = ArrayAdapter(
            mContext,
            android.R.layout.simple_spinner_item, monthElements
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter = dataAdapter
    }

    /**
     * Used to set the callback listener for when the month input is completed
     */
    fun setMonthListener(listener: MonthInput.MonthListener) {
        this.mMonthInputListener = listener
    }
}
