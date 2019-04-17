package com.checkout.sdk.billingdetails.model

import com.checkout.sdk.billingdetails.MINIMUM_BILLING_DETAIL_LENGTH


data class BillingDetail(val value: String = "") {

    fun isValid(): Boolean {
        return value.length >= MINIMUM_BILLING_DETAIL_LENGTH
    }
}
