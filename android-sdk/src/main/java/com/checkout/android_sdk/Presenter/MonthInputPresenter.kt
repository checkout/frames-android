package com.checkout.android_sdk.Presenter

import com.checkout.android_sdk.Architecture.BasePresenter
import com.checkout.android_sdk.Architecture.MvpView
import com.checkout.android_sdk.Architecture.UiState
import com.checkout.android_sdk.Store.DataStore
import com.checkout.android_sdk.UseCase.GenerateMonthsUseCase
import com.checkout.android_sdk.UseCase.MonthSelectedUseCase
import com.checkout.android_sdk.Utils.DateFormatter


class MonthInputPresenter(
    private val dateFormatter: DateFormatter,
    private val dataStore: DataStore
) : BasePresenter<MonthInputPresenter.MonthInputView, MonthInputPresenter.MonthInputUiState>(
    MonthInputUiState()
),
    GenerateMonthsUseCase.Callback,
    MonthSelectedUseCase.Callback {

    override fun initialize() {
        GenerateMonthsUseCase(dateFormatter, this).execute()
    }

    fun monthSelected(position: Int) {
        MonthSelectedUseCase(dateFormatter, position, dataStore, this).execute()
    }

    override fun onMonthsGenerated(months: Array<String>) {
        val newState = uiState.copy(months = months.asList())
        safeUpdateView(newState)
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
    ) : UiState

    interface MonthInputView : MvpView<MonthInputUiState> {
        override fun onStateUpdated(uiState: MonthInputUiState)
    }
}
