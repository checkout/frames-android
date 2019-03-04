package com.checkout.android_sdk.Presenter

import com.checkout.android_sdk.Architecture.BasePresenter
import com.checkout.android_sdk.Architecture.MvpView
import com.checkout.android_sdk.Architecture.UiState
import java.util.*


class YearInputPresenter :
    BasePresenter<YearInputPresenter.YearInputView, YearInputPresenter.YearInputUiState>(
        YearInputUiState.create(Calendar.getInstance())
    ) {

    data class YearInputUiState(val years: List<String>) : UiState {

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

    interface YearInputView : MvpView<YearInputUiState> {

    }

}
