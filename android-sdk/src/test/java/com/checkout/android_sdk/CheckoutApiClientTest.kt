package com.checkout.android_sdk

import com.checkout.android_sdk.logging.FramesLoggingEventsTest
import junit.framework.TestCase.*
import org.junit.Before
import org.junit.Test

internal class CheckoutApiClientTest {

    private lateinit var framesLoggingEventsTest: FramesLoggingEventsTest



    @Before
    internal fun setUp() {
        framesLoggingEventsTest = FramesLoggingEventsTest()
        framesLoggingEventsTest.setUp()
    }

    @Test
    fun `test CheckoutApiClientInitialisedEvent Success`() {

        assertNotNull(framesLoggingEventsTest)

        // Verify event when it is called by FramesLoggingEventsTest
        val expectedEventCount = 1
        assertEquals(expectedEventCount,
            framesLoggingEventsTest.sendFakeCheckoutApiClientInitialisedEvent())

    }

    @Test
    fun `test CheckoutApiClientInitialisedEvent Failed`() {

        assertNotNull(framesLoggingEventsTest)

        // Verify event when it is called by FramesLoggingEventsTest
        val expectedEventCount = 0
        assertNotSame(expectedEventCount,
            framesLoggingEventsTest.sendFakeCheckoutApiClientInitialisedEvent())
    }

}