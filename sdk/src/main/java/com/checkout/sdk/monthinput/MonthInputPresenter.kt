package com.checkout.sdk.monthinput

import com.checkout.sdk.architecture.BasePresenter
import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.utils.DateFormatter


class MonthInputPresenter(
    dateFormatter: DateFormatter,
    initialState: MonthInputUiState = MonthInputUiState.create(dateFormatter)
) : BasePresenter<MvpView<MonthInputUiState>, MonthInputUiState>(initialState) {

    fun monthSelected(monthSelectedUseCase: MonthSelectedUseCase) {
        val monthSelectedResult = monthSelectedUseCase.execute()
        val newState = uiState.copy(
            position = monthSelectedResult.position,
            numberString = monthSelectedResult.numberString,
            finished = true
        )
        safeUpdateView(newState)
    }
}
