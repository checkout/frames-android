package com.checkout.sdk.billingdetails

import com.checkout.sdk.architecture.UseCase


open class DoneButtonClickedUseCase(private val billingDetailsValidator: BillingDetailsValidator) :
    UseCase<BillingDetailsValidity> {

    override fun execute(): BillingDetailsValidity {
        return billingDetailsValidator.getValidity()
    }
}
