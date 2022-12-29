package com.checkout.frames.style.theme

import com.checkout.frames.model.Shape

public data class PaymentFormShape @JvmOverloads constructor(
    val inputFieldShape: Shape = Shape.Rectangle,
    val addressSummaryShape: Shape = Shape.RoundCorner,
    val buttonShape: Shape = Shape.Rectangle,
    val screenBackgroundShape: Shape = Shape.None
)
