package com.checkout.android_sdk.Presenter

import com.checkout.android_sdk.Architecture.BasePresenter
import com.checkout.android_sdk.Architecture.MvpView
import com.checkout.android_sdk.Architecture.UiState
import com.checkout.android_sdk.Store.DataStore
import com.checkout.android_sdk.UseCase.MonthSelectedUseCase
import com.checkout.android_sdk.Utils.DateFormatter
import java.text.DateFormatSymbols


class MonthInputPresenter(
    private val dateFormatter: DateFormatter,
    private val dataStore: DataStore
) : BasePresenter<MonthInputPresenter.MonthInputView, MonthInputPresenter.MonthInputUiState>(
    MonthInputUiState.create(dateFormatter)
), MonthSelectedUseCase.Callback {

    fun monthSelected(position: Int) {
        MonthSelectedUseCase(dateFormatter, position, dataStore, this).execute()
    }

    override fun onMonthSelected(position: Int, numberString: String, finished: Boolean) {
        val newState = uiState.copy(
            position = position,
            numberString = numberString,
            finished = true
        )
        safeUpdateView(newState)
    }

    data class MonthInputUiState(
        val months: List<String> = emptyList(),
        val position: Int = -1,
        val numberString: String = "", // TODO: This should probably be an Int and formatted closer to dataStore
        val finished: Boolean = false
    ) : UiState {

        companion object {
            fun create(dateFormatter: DateFormatter): MonthInputUiState {
                val months = DateFormatSymbols().shortMonths
                for (i in 0 until months.size) {
                    months[i] = months[i].toUpperCase() + " - " + dateFormatter.formatMonth(i + 1)
                }
                return MonthInputUiState(months = months.asList())
            }
        }
    }

    interface MonthInputView : MvpView<MonthInputUiState> {
        override fun onStateUpdated(uiState: MonthInputUiState)
    }
}
