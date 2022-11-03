package com.checkout.frames.screen.billingaddress.billingaddressdetails.models

import com.checkout.base.model.Country
import com.checkout.tokenization.model.Address
import com.checkout.tokenization.model.Phone
import java.util.Locale

internal data class BillingAddress(
    val name: String? = null,
    val address: Address? = null,
    var phone: Phone? = null
) {
    internal constructor() : this(
        "",
        Address("", "", "", "", "", Country.from(Locale.getDefault().country))
    )
}
