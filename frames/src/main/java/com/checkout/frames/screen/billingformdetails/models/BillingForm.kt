package com.checkout.frames.screen.billingformdetails.models

import com.checkout.base.model.Country
import com.checkout.tokenization.model.Address
import com.checkout.tokenization.model.Phone
import kotlinx.coroutines.flow.MutableStateFlow

internal data class BillingForm(
    val name: String,
    val country: MutableStateFlow<Country>,
    val address: Address? = null,
    val phone: Phone? = null
)
