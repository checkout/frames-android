package com.checkout.frames.utils.extensions

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
        it.country?.let { country ->
            strBuilder.append("\n${Locale("", country.iso3166Alpha2).displayCountry}")
        }
    }

    // Phone
    this.phone?.let { phone ->
        if (phone.number.isNotEmpty()) {
            strBuilder.append(
                if (phone.country?.dialingCode?.isNotEmpty() == true) {
                    "\n+${phone.country?.dialingCode} ${phone.number}"
                } else {
                    "\n${phone.number}"
                },
            )
        }
    }

    return strBuilder.toString().trim()
}

internal fun BillingAddress.isValid(): Boolean = when {
    this.address == null -> false
    this.address.country == null -> false
    this.phone == null -> false
    else -> true
}
