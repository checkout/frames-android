package com.checkout.frames.utils.extensions

import com.checkout.frames.screen.billingformdetails.models.BillingAddress
import java.util.Locale

internal fun BillingAddress.summary(): String = if (this.isValid()) {
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

    strBuilder.toString().trim()
} else ""

private fun BillingAddress.isValid(): Boolean = when {
    this.address == null -> false
    this.address.addressLine1.isBlank() -> false
    else -> true
}
