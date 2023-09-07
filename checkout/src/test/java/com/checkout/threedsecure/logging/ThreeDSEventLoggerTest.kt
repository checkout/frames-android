package com.checkout.threedsecure.logging

import com.checkout.base.error.CheckoutError
import com.checkout.eventlogger.domain.model.MonitoringLevel
import com.checkout.logging.Logger
import com.checkout.logging.model.LoggingEvent
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class ThreeDSEventLoggerTest {

    @RelaxedMockK
    lateinit var logger: Logger<LoggingEvent>

    private lateinit var threeDSLogger: ThreeDSLogger

    @BeforeEach
    fun setUp() {
        threeDSLogger = ThreeDSEventLogger(logger)
    }

    @Test
    fun `when log presented event is requested then event with correct data is logged`() {
        // Given
        val expected = LoggingEvent(ThreeDSEventType.PRESENTED, MonitoringLevel.INFO, mapOf())
        val capturedEvent = slot<LoggingEvent>()

        // When
        threeDSLogger.logPresentedEvent()

        // Then
        verify { logger.log(capture(capturedEvent)) }
        with(capturedEvent.captured) {
            assertEquals(expected.eventType, eventType)
            assertEquals(expected.monitoringLevel, monitoringLevel)
            assertEquals(expected.properties, properties)
        }
    }

    @Test
    fun `when log loaded event is requested then event with correct data is logged`() {
        // Given
        val expected = LoggingEvent(ThreeDSEventType.LOADED, MonitoringLevel.INFO, mapOf("success" to true))
        val capturedEvent = slot<LoggingEvent>()

        // When
        threeDSLogger.logLoadedEvent(true)

        // Then
        verify { logger.log(capture(capturedEvent)) }
        with(capturedEvent.captured) {
            assertEquals(expected.eventType, eventType)
            assertEquals(expected.monitoringLevel, monitoringLevel)
            assertEquals(expected.properties, properties)
        }
    }

    @Test
    fun `when log completed event with token is requested then event with correct data is logged`() {
        // Given
        val expected = LoggingEvent(
            ThreeDSEventType.COMPLETED,
            MonitoringLevel.INFO,
            mapOf("success" to true, "tokenID" to "testToken")
        )
        val capturedEvent = slot<LoggingEvent>()

        // When
        threeDSLogger.logCompletedEvent(true, "testToken")

        // Then
        verify { logger.log(capture(capturedEvent)) }
        with(capturedEvent.captured) {
            assertEquals(expected.eventType, eventType)
            assertEquals(expected.monitoringLevel, monitoringLevel)
            assertEquals(expected.properties, properties)
        }
    }

    @Test
    fun `when log completed event with error is requested then event with correct data is logged`() {
        // Given
        val mockError = CheckoutError("123", "testMessage", NullPointerException())
        val expected = LoggingEvent(
            ThreeDSEventType.COMPLETED,
            MonitoringLevel.ERROR,
            mapOf(
                "success" to false,
                "errorCodes" to "123",
                "message" to "testMessage",
                "exception" to (mockError).stackTraceToString()
            )
        )
        val capturedEvent = slot<LoggingEvent>()

        // When
        threeDSLogger.logCompletedEvent(false, error = mockError)

        // Then
        verify { logger.log(capture(capturedEvent)) }
        with(capturedEvent.captured) {
            assertEquals(expected.eventType, eventType)
            assertEquals(expected.monitoringLevel, monitoringLevel)
            assertEquals(expected.properties.size, properties.size)
            properties.forEach { (key, value) -> assertEquals(expected.properties[key], value) }
        }
    }
}
