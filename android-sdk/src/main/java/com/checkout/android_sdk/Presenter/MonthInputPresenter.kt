package com.checkout.android_sdk.Presenter

import com.checkout.android_sdk.Store.DataStore
import com.checkout.android_sdk.UseCase.GenerateMonthsUseCase
import com.checkout.android_sdk.UseCase.MonthSelectedUseCase
import com.checkout.android_sdk.Utils.DateFormatter


class MonthInputPresenter(private val dataStore: DataStore) : Presenter,
    GenerateMonthsUseCase.Callback,
    MonthSelectedUseCase.Callback {

    private var monthInputUiState: MonthInputUiState = MonthInputUiState()
    private var view: MonthInputView? = null

    fun start(view: MonthInputView) {
        this.view = view
        GenerateMonthsUseCase(this).execute()
    }

    fun monthSelected(position: Int) {
        MonthSelectedUseCase(DateFormatter(), position, dataStore, this).execute()
    }

    override fun onMonthsGenerated(months: Array<String>) {
        monthInputUiState = monthInputUiState.copy(months = months.asList())
        view?.onMonthInputStateUpdated(monthInputUiState)
    }

    override fun onMonthSelected(position: Int, numberString: String, finished: Boolean) {
        monthInputUiState = monthInputUiState.copy(
            position = position,
            numberString = numberString,
            finished = true
        )
        view?.onMonthInputStateUpdated(monthInputUiState)
    }

    data class MonthInputUiState(
        val months: List<String> = emptyList(),
        val position: Int = -1,
        val numberString: String = "", // TODO: This should probably be an Int and formatted closer to dataStore
        val finished: Boolean = false
    )

    interface MonthInputView {
        fun onMonthInputStateUpdated(monthInputUiState: MonthInputUiState)
    }
}
