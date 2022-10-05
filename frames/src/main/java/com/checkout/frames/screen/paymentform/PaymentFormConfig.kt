package com.checkout.frames.screen.paymentform

import android.content.Context
import com.checkout.base.model.CardScheme
import com.checkout.base.model.Environment
import com.checkout.frames.style.screen.PaymentFormStyle

public data class PaymentFormConfig(
    public var publicKey: String,
    public var context: Context,
    public var environment: Environment,
    public var style: PaymentFormStyle,
    public var supportedCardSchemeList: List<CardScheme> = emptyList()
)
