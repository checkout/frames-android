package com.checkout.tokenization

import android.content.Context
import com.checkout.base.model.Environment
import com.checkout.risk.FramesOptions
import com.checkout.risk.Risk
import com.checkout.risk.RiskConfig
import com.checkout.risk.RiskEnvironment
import com.checkout.tokenization.usecase.RiskInstanceProvider
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

public class RiskInstanceProviderTest {
    private val context: Context = mockk()
    private val framesOptions = FramesOptions("1.0.0", "productIdentifier", "correlationId")
    private val riskConfigs = mutableListOf<RiskConfig>()

    @BeforeEach
    public fun setup() {
        mockkObject(Risk.Companion)
        coEvery { Risk.getInstance(context, capture(riskConfigs)) } returns null
    }

    @AfterEach
    public fun tearDown() {
        unmockkObject(Risk.Companion)
    }

    @Test
    public fun `provide forwards base URL prefix to Risk config and preserves null`() {
        runBlocking {
            RiskInstanceProvider.provide(
                context = context,
                publicKey = "publicKey",
                environment = Environment.SANDBOX,
                framesOptions = framesOptions,
                baseUrlPrefix = "mssd",
            )
            RiskInstanceProvider.provide(
                context = context,
                publicKey = "publicKey",
                environment = Environment.PRODUCTION,
                framesOptions = framesOptions,
                baseUrlPrefix = null,
            )
        }

        assertEquals("mssd", riskConfigs[0].mssd)
        assertEquals(RiskEnvironment.SANDBOX, riskConfigs[0].environment)
        assertEquals("publicKey", riskConfigs[0].publicKey)
        assertEquals(framesOptions, riskConfigs[0].framesOptions)
        assertEquals(null, riskConfigs[1].mssd)
        assertEquals(RiskEnvironment.PRODUCTION, riskConfigs[1].environment)
    }
}
