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
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class RiskInstanceProviderTest {
    private val context: Context = mockk()
    private val framesOptions = FramesOptions("1.0.0", "productIdentifier", "correlationId")
    private val riskConfigs = mutableListOf<RiskConfig>()

    @BeforeEach
    fun setup() {
        mockkObject(Risk.Companion)
        coEvery { Risk.getInstance(context, capture(riskConfigs)) } returns null
    }

    @AfterEach
    fun tearDown() {
        unmockkObject(Risk.Companion)
    }

    @Test
    fun `valid subdomain labels are forwarded to Risk in both environments`() = runTest {
        listOf("a", "0", "Ab", "merchant-123", "a--b", "A" + "-".repeat(61) + "9").forEach { prefix ->
            provideInBothEnvironments(prefix)
            assertEquals(prefix, riskConfigs[riskConfigs.lastIndex - 1].mssd)
            assertEquals(prefix, riskConfigs.last().mssd)
        }
    }

    @Test
    fun `null empty and invalid prefixes use default Risk hosts`() = runTest {
        listOf(null, "", " ", "-", "-merchant", "merchant-", "a.b", "é", "a\n", "a".repeat(64)).forEach { prefix ->
            provideInBothEnvironments(prefix)
            assertEquals(null, riskConfigs[riskConfigs.lastIndex - 1].mssd)
            assertEquals(null, riskConfigs.last().mssd)
        }
    }

    private suspend fun provideInBothEnvironments(prefix: String?) {
        listOf(Environment.SANDBOX, Environment.PRODUCTION).forEach { environment ->
            RiskInstanceProvider.provide(context, "publicKey", environment, framesOptions, prefix)
        }
        assertEquals(RiskEnvironment.SANDBOX, riskConfigs[riskConfigs.lastIndex - 1].environment)
        assertEquals(RiskEnvironment.PRODUCTION, riskConfigs.last().environment)
        assertEquals("publicKey", riskConfigs.last().publicKey)
        assertEquals(framesOptions, riskConfigs.last().framesOptions)
    }
}
