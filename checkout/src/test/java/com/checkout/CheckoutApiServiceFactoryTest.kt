package com.checkout

import android.content.Context
import com.checkout.base.error.CheckoutError
import com.checkout.base.model.Environment
import com.checkout.logging.EventLoggerProvider
import com.checkout.logging.Logger
import com.checkout.logging.model.LoggingEvent
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
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
        every { EventLoggerProvider.provide() } returns mockLogger
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

    @Test
    fun `base URL prefix validator accepts null and alphanumeric labels up to 63 characters`() {
        listOf(null, "a", "mssd123", "a".repeat(63)).forEach { prefix ->
            BaseUrlPrefixValidator.validate(prefix)
        }
    }

    @Test
    fun `base URL prefix validator rejects labels longer than 63 characters`() {
        (64..65).forEach { length ->
            val error = assertThrows(CheckoutError::class.java) {
                BaseUrlPrefixValidator.validate("a".repeat(length))
            }

            assertEquals("CONFIGURATION_INVALID", error.errorCode, "Invalid prefix length: $length")
        }
    }

    @Test
    fun `when checkout API service is created with invalid base URL prefix then configuration invalid is thrown`() {
        val invalidPrefixes = listOf(
            "",
            " ",
            "hacked.com/",
            "evil@host",
            "a.b",
            "a-b",
            "é",
        )

        invalidPrefixes.forEach { prefix ->
            val error = assertThrows(CheckoutError::class.java) {
                CheckoutApiServiceFactory.create("", Environment.SANDBOX, mockk(), prefix)
            }

            assertEquals("CONFIGURATION_INVALID", error.errorCode, "Invalid prefix: $prefix")
        }

        verify(exactly = 0) { mockLogger.setup(any(), any()) }
    }
}
