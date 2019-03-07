package com.checkout.sdk.monthinput

import com.checkout.sdk.architecture.BasePresenter
import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.utils.DateFormatter


class MonthInputPresenter(
    dateFormatter: DateFormatter,
    initialState: MonthInputUiState = MonthInputUiState.create(dateFormatter)
) : BasePresenter<MvpView<MonthInputUiState>, MonthInputUiState>(initialState) {

    fun monthSelected(monthSelectedUseCase: MonthSelectedUseCase) {
        monthSelectedUseCase.execute()
        val newState = uiState.copy(position = monthSelectedUseCase.monthSelectedPosition)
        safeUpdateView(newState)
    }

    fun reset(monthResetUseCase: MonthResetUseCase) {
        monthResetUseCase.execute()
        val newState = MonthInputUiState()
        safeUpdateView(newState)
    }
}
