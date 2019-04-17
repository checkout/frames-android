package com.checkout.sdk.billingdetails

class BillingDetailsValidity(
    val nameValid: Boolean,
    val addressOneValid: Boolean,
    val addressTwoValid: Boolean,
    val cityValid: Boolean,
    val stateValid: Boolean,
    val zipcodeValid: Boolean,
    val countryValid: Boolean,
    val phoneValid: Boolean
    ) {
        fun areDetailsValid(): Boolean {
            return nameValid && addressOneValid && addressTwoValid && cityValid
            && stateValid && zipcodeValid && countryValid && phoneValid
        }
}
