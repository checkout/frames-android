package com.checkout.sdk.Presenter

import com.checkout.sdk.Architecture.BasePresenter
import com.checkout.sdk.Architecture.MvpView
import com.checkout.sdk.Architecture.UiState
import com.checkout.sdk.Store.DataStore
import com.checkout.sdk.UseCase.YearSelectedUseCase
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
