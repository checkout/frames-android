package com.checkout.frames.screen.billingaddress.billingaddressdetails

import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.AddressField

internal interface GetAddressField {
    fun getAddressField(
        isOptional: Boolean = false,
        addressFieldName: String
    ): AddressField
}
