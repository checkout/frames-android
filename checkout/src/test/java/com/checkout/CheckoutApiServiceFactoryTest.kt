package com.checkout

import android.content.Context
import com.checkout.base.model.Environment
import com.checkout.logging.EventLoggerProvider
import com.checkout.logging.Logger
import com.checkout.logging.model.LoggingEvent
import com.checkout.logging.utils.toBaseUrl
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.unmockkObject
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class CheckoutApiServiceFactoryTest {

    @RelaxedMockK
    lateinit var mockLogger: Logger<LoggingEvent>

    @BeforeEach
    fun setUp() {
        mockkObject(EventLoggerProvider)
        mockkStatic(Environment::toBaseUrl)
        every { EventLoggerProvider.provide() } returns mockLogger
    }

    @AfterEach
    fun tearDown() {
        unmockkObject(EventLoggerProvider)
        unmockkStatic(Environment::toBaseUrl)
    }

    @Test
    fun `valid base URL prefixes are preserved during client creation`() {
        listOf("a", "0", "Ab", "merchant-123", "a--b", "A" + "-".repeat(61) + "9").forEach { prefix ->
            CheckoutApiServiceFactory.create("", Environment.SANDBOX, mockk(), prefix)
            verify(exactly = 1) { Environment.SANDBOX.toBaseUrl(prefix) }
        }
    }

    @Test
    fun `null empty and invalid base URL prefixes are normalized to null during client creation`() {
        val prefixes = listOf(null, "", " ", "-", "-merchant", "merchant-", "a.b", "a_b", "é", "a\n", "a".repeat(64))
        prefixes.forEach { prefix ->
            CheckoutApiServiceFactory.create("", Environment.SANDBOX, mockk(), prefix)
        }
        verify(exactly = prefixes.size) { Environment.SANDBOX.toBaseUrl(null) }
    }

    @Test
    fun `when checkout API service is created then logger is set up`() {
        // Given
        val mockContext = mockk<Context>()
        val mockEnvironment = Environment.SANDBOX

        // When
        CheckoutApiServiceFactory.create("", mockEnvironment, mockContext, null)

        // Then
        verify { mockLogger.setup(eq(mockContext), eq(mockEnvironment)) }
    }
}
