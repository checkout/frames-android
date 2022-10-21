package com.checkout.frames.component.billingaddressfields

import com.checkout.frames.component.base.InputComponentState

internal data class BillingAddressInputComponentState(
    var addressFieldName: String,
    var isOptional: Boolean,
    val inputComponentState: InputComponentState
)
