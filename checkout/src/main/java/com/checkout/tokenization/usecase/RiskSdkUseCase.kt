package com.checkout.tokenization.usecase

import android.content.Context
import com.checkout.base.model.Environment
import com.checkout.base.usecase.UseCase
import com.checkout.tokenization.model.TokenResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class RiskSdkUseCase(
    private val environment: Environment,
    private val context: Context,
    private val publicKey: String,
    private val riskInstanceProvider: RiskInstanceProvider,
) : UseCase<TokenResult<String>, Unit> {
    override fun execute(data: TokenResult<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            val riskInstance = riskInstanceProvider.provide(context, publicKey, environment)
            when (data) {
                is TokenResult.Success -> {
                    riskInstance?.publishData(cardToken = data.result)
                }
                is TokenResult.Failure -> {}
            }
        }
    }
}
