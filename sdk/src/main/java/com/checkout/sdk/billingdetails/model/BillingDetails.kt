package com.checkout.sdk.billingdetails.model

import com.checkout.sdk.BillingModel
import com.checkout.sdk.models.PhoneModel

/**
 * Http request billing details object model
 */
data class BillingDetails(
    val addressOne: BillingDetail = BillingDetail(),
    val addressTwo: BillingDetail = BillingDetail(),
    val city: CityDetail = CityDetail(),
    val country: String = "",
    val postcode: String = "",
    val state: String = "",
    val phone: PhoneModel = PhoneModel()
) {

    companion object {
        fun from(billingModel: BillingModel): BillingDetails {
            return BillingDetails(
                BillingDetail(billingModel.addressOne),
                BillingDetail(billingModel.addressTwo),
                CityDetail(billingModel.city),
                billingModel.country,
                billingModel.postcode,
                billingModel.state,
                billingModel.phone
            )
        }
    }
}
