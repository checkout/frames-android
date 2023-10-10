package com.checkout.tokenization.model

import com.checkout.base.model.CardScheme

internal data class ValidateCVVTokenizationRequest(
    val cvv: String,
    val cardScheme: CardScheme = CardScheme.UNKNOWN,
)
