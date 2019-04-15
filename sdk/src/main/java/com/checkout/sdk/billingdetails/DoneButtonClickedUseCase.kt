package com.checkout.sdk.billingdetails

import com.checkout.sdk.architecture.UseCase


class DoneButtonClickedUseCase(private val billingFormValidator: BillingFormValidator) :
    UseCase<BillingDetailsValidity> {

    override fun execute(): BillingDetailsValidity {
        return billingFormValidator.getValidity()
    }
}
