package com.checkout.tokenization.usecase

import android.content.Context
import com.checkout.base.model.Environment
import com.checkout.base.usecase.UseCase
import com.checkout.risk.Risk
import com.checkout.risk.RiskConfig
import com.checkout.risk.RiskEnvironment
import com.checkout.tokenization.model.TokenDetails
import com.checkout.tokenization.model.TokenResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class RiskSdkUseCase(
    private val environment: Environment,
    private val context: Context,
    private val publicKey: String,
    private val riskInstanceProvider: RiskInstanceProvider = RiskInstanceProvider(),
) : UseCase<TokenResult<TokenDetails>, Unit> {
    override fun execute(data: TokenResult<TokenDetails>) {
        CoroutineScope(Dispatchers.IO).launch {
            val riskInstance = riskInstanceProvider.provide(context, publicKey, environment)
            when (data) {
                is TokenResult.Success -> {
                    riskInstance?.publishData(cardToken = data.result.token)
                }
                is TokenResult.Failure -> {}
            }
        }
    }
}

internal class RiskInstanceProvider() {
    suspend fun provide(
        context: Context,
        publicKey: String,
        environment: Environment,
    ): Risk? {
        val riskEnvironment =
            when (environment) {
                Environment.PRODUCTION -> RiskEnvironment.PRODUCTION
                Environment.SANDBOX -> RiskEnvironment.SANDBOX
            }

        return Risk.getInstance(
            context,
            RiskConfig(
                publicKey = publicKey,
                environment = riskEnvironment,
                framesMode = true,
            ),
        )
    }
}
