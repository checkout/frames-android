package com.checkout.sdk.monthinput

import com.checkout.sdk.architecture.BasePresenter
import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.architecture.UiState
import com.checkout.sdk.store.DataStore
import com.checkout.sdk.utils.DateFormatter
import java.text.DateFormatSymbols


class MonthInputPresenter(
    private val dateFormatter: DateFormatter,
    private val dataStore: DataStore
) : BasePresenter<MonthInputPresenter.MonthInputView, MonthInputPresenter.MonthInputUiState>(
    MonthInputUiState.create(dateFormatter)
) {

    fun monthSelected(position: Int) {
        val monthSelectedResult = MonthSelectedUseCase(
            dateFormatter,
            position,
            dataStore
        ).execute()
        val newState = uiState.copy(
            position = monthSelectedResult.position,
            numberString = monthSelectedResult.numberString,
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

    interface MonthInputView : MvpView<MonthInputUiState>
}
