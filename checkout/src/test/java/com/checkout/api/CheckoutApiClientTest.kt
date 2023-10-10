package com.checkout.api

import com.checkout.api.logging.ApiClientEventType
import com.checkout.eventlogger.domain.model.MonitoringLevel
import com.checkout.logging.EventLoggerProvider
import com.checkout.logging.Logger
import com.checkout.logging.model.LoggingEvent
import com.checkout.threedsecure.Executor
import com.checkout.threedsecure.model.ThreeDSRequest
import com.checkout.tokenization.model.CVVTokenizationRequest
import com.checkout.tokenization.model.CardTokenRequest
import com.checkout.tokenization.model.GooglePayTokenRequest
import com.checkout.tokenization.repository.TokenRepository
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.slot
import io.mockk.verify
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class CheckoutApiClientTest {

    @RelaxedMockK
    lateinit var mockTokenRepository: TokenRepository

    @RelaxedMockK
    lateinit var mockLogger: Logger<LoggingEvent>

    @RelaxedMockK
    lateinit var mockThreeDSExecutor: Executor<ThreeDSRequest>

    private lateinit var checkoutApiService: CheckoutApiService

    @BeforeEach
    fun setUp() {
        mockkObject(EventLoggerProvider)
        every { EventLoggerProvider.provide() } returns mockLogger

        checkoutApiService = CheckoutApiClient(
            mockTokenRepository,
            mockThreeDSExecutor,
        )
    }

    @Test
    fun `when api client is initialised then initialisation event is logged`() {
        // Given
        val expected = LoggingEvent(ApiClientEventType.INITIALIZE, MonitoringLevel.INFO, emptyMap())
        val capturedEvent = slot<LoggingEvent>()

        // Then
        verify { mockLogger.log(capture(capturedEvent)) }
        with(capturedEvent.captured) {
            assertEquals(expected.eventType, eventType)
            assertEquals(expected.monitoringLevel, monitoringLevel)
            assertEquals(expected.properties, properties)
        }
    }

    @Test
    fun `when create token requested then send card token request with correct data is invoked`() {
        // Given
        val mockRequest = mockk<CardTokenRequest>()

        // When
        checkoutApiService.createToken(mockRequest)

        // Then
        verify { mockTokenRepository.sendCardTokenRequest(eq(mockRequest)) }
    }

    @Test
    fun `when create token requested then send cvv token request with correct data is invoked`() {
        // Given
        val mockRequest = mockk<CVVTokenizationRequest>()

        // When
        checkoutApiService.createToken(mockRequest)

        // Then
        verify { mockTokenRepository.sendCVVTokenizationRequest(eq(mockRequest)) }
    }

    @Test
    fun `when create token requested then send google pay token request with correct data is invoked`() {
        // Given
        val mockRequest = mockk<GooglePayTokenRequest>()

        // When
        checkoutApiService.createToken(mockRequest)

        // Then
        verify { mockTokenRepository.sendGooglePayTokenRequest(eq(mockRequest)) }
    }

    @Test
    fun `when handle of 3DS requested then 3DS handling with correct data is executed`() {
        // Given
        val mockRequest = mockk<ThreeDSRequest>()

        // When
        checkoutApiService.handleThreeDS(mockRequest)

        // Then
        verify { mockThreeDSExecutor.execute(eq(mockRequest)) }
    }
}
