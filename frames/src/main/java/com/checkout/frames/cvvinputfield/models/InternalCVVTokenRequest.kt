package com.checkout.frames.cvvinputfield.models

import com.checkout.base.model.CardScheme
import com.checkout.tokenization.model.CVVTokenizationResultHandler

internal data class InternalCVVTokenRequest(
    val cvv: String,
    val cardScheme: CardScheme = CardScheme.UNKNOWN,
    val resultHandler: (CVVTokenizationResultHandler) -> Unit,
)
