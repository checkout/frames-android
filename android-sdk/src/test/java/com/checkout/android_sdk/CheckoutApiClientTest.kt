package com.checkout.android_sdk

import android.content.Context
import com.checkout.android_sdk.Utils.Environment
import com.checkout.eventlogger.CheckoutEventLogger
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
        mockFramesLogger.initialise(mock(Context::class.java), Environment.SANDBOX, sdkLoggerMock)
    }

    @Test
    fun `test Success Logging of CheckoutApiClientInitialisedEvent`() {
        mockFramesLogger.sendCheckoutApiClientInitialisedEvent(Environment.SANDBOX)
        verify(mockFramesLogger,
            times(1)).sendCheckoutApiClientInitialisedEvent(Environment.SANDBOX)
    }



}