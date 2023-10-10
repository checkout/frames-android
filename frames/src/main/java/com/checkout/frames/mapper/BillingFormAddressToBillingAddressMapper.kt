package com.checkout.frames.mapper

import com.checkout.base.mapper.Mapper
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingAddress
import com.checkout.frames.screen.paymentform.model.BillingFormAddress

internal class BillingFormAddressToBillingAddressMapper : Mapper<BillingFormAddress?, BillingAddress> {

    override fun map(from: BillingFormAddress?) = BillingAddress(
        name = from?.name,
        phone = from?.phone,
        address = from?.address,
    )

    internal companion object {
        internal val INSTANCE by lazy { BillingFormAddressToBillingAddressMapper() }
    }
}
