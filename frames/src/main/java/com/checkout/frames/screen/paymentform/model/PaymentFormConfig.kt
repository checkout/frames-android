package com.checkout.frames.screen.paymentform.model

import android.content.Context
import com.checkout.base.model.CardScheme
import com.checkout.base.model.Environment
import com.checkout.frames.api.PaymentFlowHandler
import com.checkout.frames.style.screen.PaymentFormStyle

/**
 * @param publicKey - used for client-side authentication in the SDK
 * @param context - represent the application context
 * @param environment - [Environment] represent the environment for tokenization
 * @param style - [PaymentFormStyle] represent the style for PaymentForm
 * @param paymentFlowHandler - [PaymentFlowHandler] represent the handler for PaymentForm
 * @param supportedCardSchemeList - represent the supported card schemes [CardScheme] in PaymentForm
 * @param prefillData - [PrefillData] represent the data for prefill in the PaymentForm
 */
public data class PaymentFormConfig @JvmOverloads constructor(
    public val publicKey: String,
    public val context: Context,
    public val environment: Environment,
    public var style: PaymentFormStyle,
    public val paymentFlowHandler: PaymentFlowHandler,
    public var supportedCardSchemeList: List<CardScheme> = emptyList(),
    public val prefillData: PrefillData? = null
)
