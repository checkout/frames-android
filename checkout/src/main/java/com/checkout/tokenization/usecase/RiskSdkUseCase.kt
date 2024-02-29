package com.checkout.tokenization.usecase

import android.content.Context
import com.checkout.base.model.Environment
import com.checkout.base.usecase.UseCase
import com.checkout.risk.PublishDataResult
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
) : UseCase<TokenResult<TokenDetails>, Unit> {
    override fun execute(data: TokenResult<TokenDetails>) {
        val riskEnvironment =
            when (environment) {
                Environment.PRODUCTION -> RiskEnvironment.PRODUCTION
                Environment.SANDBOX -> RiskEnvironment.SANDBOX
            }

        CoroutineScope(Dispatchers.IO).launch {
            val riskInstance =
                Risk.getInstance(
                    context,
                    RiskConfig(
                        publicKey = publicKey,
                        environment = riskEnvironment,
                        framesMode = true,
                    ),
                ).let {
                    it ?: run {
                        null
                    }
                }

            when (data) {
                is TokenResult.Success -> {
                    riskInstance?.publishData(cardToken = data.result.token)
                }
                is TokenResult.Failure -> {}
            }
        }
    }
}
