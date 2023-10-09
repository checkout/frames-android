package com.checkout.tokenization.logging

import com.checkout.eventlogger.domain.model.MonitoringLevel
import com.checkout.logging.Logger
import com.checkout.logging.model.LoggingEvent
import com.checkout.mock.TokenizationRequestTestData
import com.checkout.tokenization.error.TokenizationError
import com.checkout.tokenization.utils.TokenizationConstants
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class TokenizationEventLoggerTest {

    @RelaxedMockK
    lateinit var mockLogger: Logger<LoggingEvent>

    private lateinit var tokenizationEventLogger: TokenizationEventLogger

    @BeforeEach
    fun setUp() {
        tokenizationEventLogger = TokenizationEventLogger(mockLogger)
    }

    @Test
    fun `when log token request event for card is requested then event with correct data is logged`() {
        testResultForLogTokenRequest(TokenizationConstants.CARD)
    }

    @Test
    fun `when log token request event for google pay is requested then event with correct data is logged`() {
        testResultForLogTokenRequest(TokenizationConstants.GOOGLE_PAY)
    }

    @Test
    fun `when log token request event with error for google pay is requested then event with correct data is logged`() {
        // Given
        val mockTokenizationError = TokenizationError(
            "123",
            "testMessage",
            NullPointerException()
        )

        val expected = LoggingEvent(
            TokenizationEventType.TOKEN_REQUESTED,
            MonitoringLevel.ERROR,
            mapOf(
                "tokenType" to TokenizationConstants.GOOGLE_PAY,
                "publicKey" to "test_key",
                "errorCodes" to "123",
                "message" to "testMessage",
                "exception" to (mockTokenizationError).stackTraceToString()
            )
        )
        val capturedEvent = slot<LoggingEvent>()

        // When
        tokenizationEventLogger.logErrorOnTokenRequestedEvent(
            TokenizationConstants.GOOGLE_PAY,
            "test_key", error = mockTokenizationError
        )

        // Then
        verify { mockLogger.log(capture(capturedEvent)) }
        with(capturedEvent.captured) {
            assertEquals(expected.eventType, eventType)
            assertEquals(expected.monitoringLevel, monitoringLevel)
            assertEquals(expected.properties.size, properties.size)
            properties.forEach { (key, value) -> assertEquals(expected.properties[key], value) }
        }
    }

    @Test
    fun `when log success token response event for card is requested then event with correct data is logged`() {
        testResultForLogSuccessTokenResponse(TokenizationConstants.CARD)
    }

    @Test
    fun `when log success token response event for google pay is requested then event with correct data is logged`() {
        testResultForLogSuccessTokenResponse(TokenizationConstants.GOOGLE_PAY)
    }

    @Test
    fun `when log server error token response event for card is requested then event with correct data is logged`() {
        testResultForLogServerErrorTokenResponse(TokenizationConstants.CARD)
    }

    @Test
    fun `when log server error token response event for google pay is requested then event with correct data is logged`() {
        testResultForLogServerErrorTokenResponse(TokenizationConstants.GOOGLE_PAY)
    }

    @Test
    fun `when log reset session event is requested then event is logged`() {
        // When
        tokenizationEventLogger.resetSession()

        // Then
        verify { mockLogger.resetSession() }
    }

    private fun testResultForLogTokenRequest(tokenType: String) {
        // Given
        val expected = LoggingEvent(
            TokenizationEventType.TOKEN_REQUESTED,
            MonitoringLevel.INFO,
            mapOf("tokenType" to tokenType, "publicKey" to "test_key")
        )
        val capturedEvent = slot<LoggingEvent>()

        // When
        tokenizationEventLogger.logTokenRequestEvent(tokenType, "test_key")

        // Then
        verify { mockLogger.log(capture(capturedEvent)) }
        with(capturedEvent.captured) {
            assertEquals(expected.eventType, eventType)
            assertEquals(expected.monitoringLevel, monitoringLevel)
            assertEquals(expected.properties, properties)
        }
    }

    private fun testResultForLogSuccessTokenResponse(tokenType: String) {
        // Given
        val expected = LoggingEvent(
            TokenizationEventType.TOKEN_RESPONSE,
            MonitoringLevel.INFO,
            mapOf(
                "tokenType" to tokenType,
                "publicKey" to "test_key",
                "tokenID" to "tok_test",
                "scheme" to "VISA"
            )
        )

        val capturedEvent = slot<LoggingEvent>()

        // When
        tokenizationEventLogger.logTokenResponseEvent(
            tokenType = tokenType,
            publicKey = "test_key",
            tokenDetails = TokenizationRequestTestData.tokenDetailsResponse()
        )

        // Then
        verify { mockLogger.log(capture(capturedEvent)) }
        with(capturedEvent.captured) {
            assertEquals(expected.eventType, eventType)
            assertEquals(expected.monitoringLevel, monitoringLevel)
            assertEquals(expected.properties, properties)
        }
    }

    private fun testResultForLogServerErrorTokenResponse(tokenType: String) {
        // Given
        val expected = LoggingEvent(
            TokenizationEventType.TOKEN_RESPONSE,
            MonitoringLevel.INFO,
            mapOf(
                "publicKey" to "test_key",
                "tokenType" to tokenType,
                "serverError" to mapOf(
                    "requestID" to "testID",
                    "errorType" to "testErrorType",
                    "errorCodes" to listOf("testErrorCodes"),
                ),
                "httpStatusCode" to 501,
            )
        )

        val capturedEvent = slot<LoggingEvent>()

        // When
        tokenizationEventLogger.logTokenResponseEvent(
            tokenType,
            "test_key",
            null,
            501,
            TokenizationRequestTestData.errorResponse()
        )

        // Then
        verify { mockLogger.log(capture(capturedEvent)) }

        with(capturedEvent.captured) {
            assertEquals(expected.eventType, eventType)
            assertEquals(expected.monitoringLevel, monitoringLevel)
            assertEquals(expected.properties.size, properties.size)
            properties.forEach { (key, value) -> assertEquals(expected.properties[key], value) }
        }
    }
}
