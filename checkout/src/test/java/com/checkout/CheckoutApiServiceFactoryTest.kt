package com.checkout

import android.content.Context
import com.checkout.base.model.Environment
import com.checkout.logging.EventLoggerProvider
import com.checkout.logging.Logger
import com.checkout.logging.model.LoggingEvent
import io.mockk.mockkObject
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
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
        CheckoutApiServiceFactory.create("", mockEnvironment, mockContext)

        // Then
        verify { mockLogger.setup(eq(mockContext), eq(mockEnvironment)) }
    }
}
