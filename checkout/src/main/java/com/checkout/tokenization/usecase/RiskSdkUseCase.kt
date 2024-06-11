package com.checkout.tokenization.usecase

import android.content.Context
import com.checkout.base.model.Environment
import com.checkout.risk.FramesOptions
import com.checkout.tokenization.model.TokenResult

internal class RiskSdkUseCase(
    private val environment: Environment,
    private val context: Context,
    private val publicKey: String,
    private val framesOptions: FramesOptions,
    private val riskInstanceProvider: RiskInstanceProvider,
) {
    suspend fun execute(data: TokenResult<String>) {
        val riskInstance = riskInstanceProvider.provide(context, publicKey, environment, framesOptions)
        when (data) {
            is TokenResult.Success -> riskInstance?.publishData(cardToken = data.result)
            is TokenResult.Failure -> {}
        }
    }
}
