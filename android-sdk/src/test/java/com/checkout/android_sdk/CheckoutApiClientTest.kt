package com.checkout.android_sdk

import android.content.Context
import com.checkout.android_sdk.Utils.Environment
import com.checkout.eventlogger.CheckoutEventLogger
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*


internal class CheckoutApiClientTest {


    private lateinit var mockFramesLogger: FramesLogger

    private lateinit var sdkLoggerMock: CheckoutEventLogger


    @Before
    internal fun setUp() {
        sdkLoggerMock = mock(CheckoutEventLogger::class.java)
        mockFramesLogger = mock(FramesLogger::class.java)
    }

    @Test
    fun `test success logging of checkoutApiClientInitialisedEvent for live environment`() {
        mockFramesLogger.initialise(mock(Context::class.java), Environment.LIVE, sdkLoggerMock)

        val checkoutAPIClientMock = CheckoutAPIClient(
            mock(Context::class.java),
            "test_key",
            Environment.LIVE, mockFramesLogger
        )

        val expectedEnvironment = Environment.LIVE
        assertEquals(expectedEnvironment, checkoutAPIClientMock.environment)

        verify(mockFramesLogger,
            times(1)).sendCheckoutApiClientInitialisedEvent(Environment.LIVE)
    }

    @Test
    fun `test success logging of checkoutApiClientInitialisedEvent for sandbox environment`() {
        mockFramesLogger.initialise(mock(Context::class.java), Environment.SANDBOX, sdkLoggerMock)

        val checkoutAPIClientMock = CheckoutAPIClient(
            mock(Context::class.java),
            "test_key",
            Environment.SANDBOX, mockFramesLogger
        )

        val expectedEnvironment = Environment.SANDBOX
        assertEquals(expectedEnvironment, checkoutAPIClientMock.environment)

        verify(mockFramesLogger,
            times(1)).sendCheckoutApiClientInitialisedEvent(Environment.SANDBOX)
    }


}