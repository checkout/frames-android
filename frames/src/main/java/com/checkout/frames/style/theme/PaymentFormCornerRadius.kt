package com.checkout.frames.style.theme

import com.checkout.frames.model.CornerRadius
import com.checkout.frames.utils.constants.BorderConstants
import com.checkout.frames.utils.constants.ContainerConstants

public data class PaymentFormCornerRadius @JvmOverloads constructor(
    val inputFieldCornerRadius: CornerRadius = CornerRadius(ContainerConstants.radius),
    val addressSummaryCornerRadius: CornerRadius = CornerRadius(BorderConstants.radius),
    val buttonCornerRadius: CornerRadius = CornerRadius(),
    val screenBackgroundCornerRadius: CornerRadius = CornerRadius(ContainerConstants.radius)
)
