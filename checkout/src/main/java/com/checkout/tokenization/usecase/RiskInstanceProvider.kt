package com.checkout.tokenization.usecase

import android.content.Context
import com.checkout.BaseUrlPrefixValidator
import com.checkout.base.model.Environment
import com.checkout.risk.FramesOptions
import com.checkout.risk.Risk
import com.checkout.risk.RiskConfig
import com.checkout.risk.RiskEnvironment

internal object RiskInstanceProvider {
    private var riskInstance: Risk? = null

    suspend fun provide(
        context: Context,
        publicKey: String,
        environment: Environment,
        framesOptions: FramesOptions,
        baseUrlPrefix: String?,
    ): Risk? {
        if (riskInstance != null) {
            return riskInstance
        }

        val riskEnvironment =
            when (environment) {
                Environment.PRODUCTION -> RiskEnvironment.PRODUCTION
                Environment.SANDBOX -> RiskEnvironment.SANDBOX
            }

        riskInstance =
            Risk.getInstance(
                context,
                RiskConfig(
                    publicKey = publicKey,
                    environment = riskEnvironment,
                    framesOptions = framesOptions,
                    mssd = BaseUrlPrefixValidator.validPrefixOrNull(baseUrlPrefix),
                ),
            )
        return riskInstance
    }
}
