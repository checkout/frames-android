package com.checkout.android_sdk.UseCase

import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CardFocusUseCaseTest {

    @Test
    fun `given valid card without focus then card focus result is error`() {
        val showError = CardFocusUseCase(false, "1234567812345678").execute()

        assertEquals(showError, true)
    }

    @Test
    fun `given valid card with focus then card focus result is no error`() {
        val showError = CardFocusUseCase(true, "4242424242424242").execute()

        assertEquals(showError, false)
    }

    @Test
    fun `given invalid card without focus then card focus result is error`() {
        val showError = CardFocusUseCase(false, "11").execute()

        assertEquals(showError, true)
    }

}
