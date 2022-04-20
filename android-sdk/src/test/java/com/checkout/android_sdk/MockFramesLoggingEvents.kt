package com.checkout.android_sdk

/**
 * This class is used to pass event and update the event counter which is called to unit test
 */
class MockFramesLoggingEvents constructor(
    var eventLoggedCount: Int,
    private val framesLoggingEvent: TestFramesLoggingEvent,
) {

    fun callCheckoutApiClientInitialisedEventTest(): TestFramesLoggingEvent {
        eventLoggedCount++

        return framesLoggingEvent
    }
}