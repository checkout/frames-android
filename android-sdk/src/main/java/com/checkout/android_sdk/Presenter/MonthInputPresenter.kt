package com.checkout.android_sdk.Presenter

import com.checkout.android_sdk.UseCase.MonthInputPopulateUseCase


class MonthInputPresenter : Presenter, MonthInputPopulateUseCase.Callback {

    private var view: MonthInputView? = null

    fun start(view: MonthInputView) {
        this.view = view
        MonthInputPopulateUseCase(this).execute()
    }

    override fun onMonthsGenerated(months: Array<String>) {
        view?.onMonthsGenerated(months)
    }

    interface MonthInputView {
        fun onMonthsGenerated(months: Array<String>)
    }
}
