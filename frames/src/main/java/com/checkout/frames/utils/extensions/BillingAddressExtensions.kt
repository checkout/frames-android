package com.checkout.frames.utils.extensions

import com.checkout.base.model.Country
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingAddress
import java.util.Locale

internal fun BillingAddress.summary(): String {
    val strBuilder = StringBuilder()

    // Full name
    if (this.name?.isNotEmpty() == true) strBuilder.append(this.name)
    // Main address data
    this.address?.let {
        if (it.addressLine1.isNotEmpty()) strBuilder.append("\n${it.addressLine1}")
        if (it.addressLine2.isNotEmpty()) strBuilder.append("\n${it.addressLine2}")
        if (it.city.isNotEmpty()) strBuilder.append("\n${it.city}")
        if (it.state.isNotEmpty()) strBuilder.append("\n${it.state}")
        if (it.zip.isNotEmpty()) strBuilder.append("\n${it.zip}")
        strBuilder.append("\n${Locale("", it.country.iso3166Alpha2).displayCountry}")
    }
    // Phone
    if (this.phone?.number?.isNotEmpty() == true)
        strBuilder.append("\n+${this.phone.country.dialingCode} ${this.phone.number}")

    return strBuilder.toString().trim()
}

internal fun BillingAddress.isValid(): Boolean = when {
    this.address == null -> false
    this.address.country == Country.INVALID_COUNTRY -> false
    this.phone == null -> false
    else -> true
}
