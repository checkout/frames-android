package com.checkout.sdk.presenter

import com.checkout.sdk.architecture.BasePresenter
import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.architecture.UiState
import com.checkout.sdk.store.DataStore
import com.checkout.sdk.usecase.YearSelectedUseCase
import java.util.*


class YearInputPresenter(
    private val dataStore: DataStore,
    initialState: YearInputUiState = YearInputUiState.create(
        Calendar.getInstance()
    )
) :
    BasePresenter<YearInputPresenter.YearInputView, YearInputPresenter.YearInputUiState>(
        initialState
    ) {

    fun yearSelected(position: Int) {
        YearSelectedUseCase(dataStore, uiState.years, position).execute()
        safeUpdateView(uiState.copy(position = position))
    }

    data class YearInputUiState(val years: List<String>, val position: Int = -1) : UiState {

        companion object {
            private const val MAX_YEARS_IN_FUTURE = 15

            fun create(calendar: Calendar): YearInputUiState {
                val years = mutableListOf<String>()
                val thisYear = calendar.get(Calendar.YEAR)
                for (i in thisYear until thisYear + MAX_YEARS_IN_FUTURE) {
                    years.add(i.toString())
                }
                return YearInputUiState(years)
            }
        }

    }

    interface YearInputView : MvpView<YearInputUiState>
}
