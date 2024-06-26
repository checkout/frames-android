package com.checkout.tokenization

import android.content.Context
import com.checkout.base.error.CheckoutError
import com.checkout.base.model.Environment
import com.checkout.risk.FramesOptions
import com.checkout.risk.Risk
import com.checkout.tokenization.model.TokenDetails
import com.checkout.tokenization.model.TokenResult
import com.checkout.tokenization.usecase.RiskInstanceProvider
import com.checkout.tokenization.usecase.RiskSdkUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

public class RiskSdkUseCaseTest {
    private val correlationId: String = "testCorrelationId"
    private val version: String = "1.0.0"
    private val productIdentifier: String = "productIdentifier"
    private val framesOptions: FramesOptions = FramesOptions(version, productIdentifier, correlationId)
    private val environment: Environment = Environment.SANDBOX
    private val riskInstanceProvider: RiskInstanceProvider = mockk()
    private val riskInstance: Risk = mockk()
    private val context: Context = mockk()
    private val tokenDetails: TokenDetails = mockk()
    private lateinit var useCase: RiskSdkUseCase

    @BeforeEach
    public fun setup() {
        coEvery { tokenDetails.token } returns TOKEN
        coEvery { riskInstanceProvider.provide(context, PUBLIC_KEY, environment, framesOptions) } returns riskInstance
        coEvery { riskInstance.publishData(any()) } returns mockk()
        useCase =
            RiskSdkUseCase(
                environment = environment,
                context = context,
                publicKey = PUBLIC_KEY,
                riskInstanceProvider = riskInstanceProvider,
                framesOptions = framesOptions,
            )
    }

    @Test
    public fun `Success result should trigger publishData`() {
        runBlocking {
            useCase.execute(TokenResult.Success(tokenDetails.token))
            coVerify { riskInstanceProvider.provide(context, PUBLIC_KEY, environment, framesOptions) }
            coVerify { riskInstance.publishData(TOKEN) }
        }
    }

    @Test
    public fun `Failed result should not trigger publishData`() {
        runBlocking {
            launch {
                useCase.execute(TokenResult.Failure(CheckoutError("error", "message")))
                coVerify(exactly = 0) { riskInstance.publishData(any()) }
            }
        }
    }

    private companion object {
        private const val PUBLIC_KEY: String = "pk_123"
        private const val TOKEN = "TOKEN"
    }
}
