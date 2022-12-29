package com.checkout.frames.screen.paymentform

import android.content.Context
import com.checkout.base.model.CardScheme
import com.checkout.base.model.Environment
import com.checkout.frames.style.screen.PaymentFormStyle
import com.checkout.frames.api.PaymentFlowHandler

public data class PaymentFormConfig @JvmOverloads constructor(
    public val publicKey: String,
    public val context: Context,
    public val environment: Environment,
    public var style: PaymentFormStyle,
    public val paymentFlowHandler: PaymentFlowHandler,
    public var supportedCardSchemeList: List<CardScheme> = emptyList()
)
