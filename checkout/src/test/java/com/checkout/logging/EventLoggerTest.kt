package com.checkout.logging

import android.content.Context
import com.checkout.BuildConfig
import com.checkout.base.model.Environment
import com.checkout.eventlogger.CheckoutEventLogger
import com.checkout.eventlogger.METADATA_CORRELATION_ID
import com.checkout.eventlogger.domain.model.Event
import com.checkout.eventlogger.domain.model.MetadataKey
import com.checkout.eventlogger.domain.model.MonitoringLevel
import com.checkout.eventlogger.domain.model.RemoteProcessorMetadata
import com.checkout.logging.model.LoggingEvent
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class EventLoggerTest {
    @RelaxedMockK
    lateinit var mockLogger: CheckoutEventLogger

    private lateinit var eventLogger: Logger<LoggingEvent>

    @BeforeEach
    fun setUp() {
        eventLogger = spyk(EventLogger(mockLogger))
    }

    @Test
    fun `when setup requested then remote processor is enabled with correct metadata`() {
        // Given
        val mockContext: Context = mockk(relaxed = true)
        val mockEnvironment = Environment.SANDBOX
        val expectedMetadata =
            RemoteProcessorMetadata.from(
                mockContext,
                "sandbox",
                BuildConfig.PRODUCT_IDENTIFIER,
                BuildConfig.PRODUCT_VERSION,
            )

        // When
        eventLogger.setup(mockContext, mockEnvironment)

        // Then
        verify {
            mockLogger.enableRemoteProcessor(
                eq(com.checkout.eventlogger.Environment.SANDBOX),
                eq(expectedMetadata),
            )
        }
    }

    @Test
    fun `when setup requested then session reset is invoked`() {
        // Given
        val mockContext: Context = mockk(relaxed = true)
        val mockEnvironment = Environment.PRODUCTION

        // When
        eventLogger.setup(mockContext, mockEnvironment)

        // Then
        verify { eventLogger.resetSession() }
    }

    @Test
    fun `when setup requested but it has been already done then request is ignored`() {
        // Given
        val mockContext: Context = mockk(relaxed = true)
        val mockEnvironment = Environment.SANDBOX
        (eventLogger as? EventLogger)?.needToSetup = false

        // When
        eventLogger.setup(mockContext, mockEnvironment)

        // Then
        verify(exactly = 0) { eventLogger.resetSession() }
        verify(exactly = 0) { mockLogger.enableRemoteProcessor(environment = any(), any()) }
    }

    @Test
    fun `when reset is invoked then correlation ID is updated in logger metadata`() {
        // When
        eventLogger.resetSession()

        // Then
        verify { mockLogger.addMetadata(MetadataKey.correlationId, any()) }
    }

    @Test
    fun `when reset is invoked then sent logs cache is reset`() {
        // Given
        (eventLogger as? EventLogger)?.sentLogs?.add("test_id")

        // When
        eventLogger.resetSession()

        // Then
        verify { mockLogger.addMetadata(METADATA_CORRELATION_ID, any()) }
        assertTrue((eventLogger as? EventLogger)?.sentLogs?.isEmpty() == true)
    }

    @Test
    fun `when log event requested then a correct event is logged`() {
        // Given
        val mockID = "testID"
        val mockEventType =
            object : LoggingEventType {
                override val eventId: String = mockID
            }
        val mockMonitoringLevel = MonitoringLevel.INFO
        val mockProperties = mapOf("test" to "for test")
        val mockEvent = LoggingEvent(mockEventType, mockMonitoringLevel, mockProperties)
        val capturedEvent = slot<Event>()

        every { mockLogger.logEvent(capture(capturedEvent)) } returns Unit

        // When
        eventLogger.log(mockEvent)

        // Then
        assertEquals(capturedEvent.captured.monitoringLevel, mockMonitoringLevel)
        assertEquals(capturedEvent.captured.time, mockEvent.time)
        assertEquals(capturedEvent.captured.typeIdentifier, mockID)
        assertEquals(capturedEvent.captured.properties, mockProperties)
    }

    @Test
    fun `when log event once requested and event wasn't sent before then event is logged`() {
        // Given
        val mockID = "test_ID"
        val mockEventType =
            object : LoggingEventType {
                override val eventId: String = mockID
            }
        val mockMonitoringLevel = MonitoringLevel.INFO
        val mockProperties = mapOf("test1" to "for test1")
        val mockEvent = LoggingEvent(mockEventType, mockMonitoringLevel, mockProperties)

        (eventLogger as? EventLogger)?.sentLogs?.clear()

        // When
        eventLogger.logOnce(mockEvent)

        // Then
        verify(exactly = 1) { mockLogger.logEvent(mockEvent) }
    }

    @Test
    fun `when log event once requested and event with such a type has been already sent then event is not logged`() {
        // Given
        val mockID = "test_ID"
        val mockEventType =
            object : LoggingEventType {
                override val eventId: String = mockID
            }
        val mockMonitoringLevel = MonitoringLevel.INFO
        val mockProperties = mapOf("test1" to "for test1")
        val mockEvent = LoggingEvent(mockEventType, mockMonitoringLevel, mockProperties)

        (eventLogger as? EventLogger)?.sentLogs?.add(mockID)

        // When
        eventLogger.logOnce(mockEvent)

        // Then
        verify(exactly = 0) { mockLogger.logEvent(mockEvent) }
    }
}
